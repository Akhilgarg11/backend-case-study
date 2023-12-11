package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Cart;
import com.entity.CartItem;
import com.entity.ProductDetails;
import com.entity.UserDetails;
import com.repository.CartItemRepository;
import com.repository.CartRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository itemRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    /**
     * Adds a product to the user's cart.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to add.
     * @param quantity  The quantity of the product to add.
     * @return The created CartItem.
     * @throws EntityNotFoundException If the user or product is not found.
     */
    @Transactional
    public CartItem addToCart(int userId, int productId, int quantity) {
        UserDetails user;

        // Retrieve the user by ID
        Optional<UserDetails> getUser = userRepo.findById(userId);
        if (getUser.isPresent()) {
            user = getUser.get();
        } else {
            throw new EntityNotFoundException("User Not Exist");
        }

        // Retrieve the user's cart
        Cart cart = userRepo.findCartByUserId(userId);

        // If the user doesn't have a cart, create a new one
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        }

        // Retrieve the product by ID
        Optional<ProductDetails> optional = productRepo.findById(productId);
        ProductDetails product = optional.orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Check if the product is already in the cart
        Optional<CartItem> existingCartItem = itemRepo.findByCartAndProduct(cart, product);
        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            // If the product is already in the cart, update the quantity
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // If the product is not in the cart, create a new cart item
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
        }

        // Save the cart item, update user's cart, and save the cart
        itemRepo.save(cartItem);
        user.setCart(cart);
        userRepo.save(user);
        cartRepo.save(cart);

        return cartItem;
    }

    /**
     * Retrieves the cart for the specified user.
     *
     * @param userId The ID of the user.
     * @return The user's cart.
     */
    public Cart getCart(int userId) {
        // Retrieve the user's cart by ID
        Cart cart = userRepo.findCartByUserId(userId);
        return cart;
    }

    /**
     * Retrieves a specific cart item by user and item ID.
     *
     * @param userId The ID of the user.
     * @param itemId The ID of the cart item.
     * @return The specified CartItem.
     */
    public CartItem getCartItem(int userId, int itemId) {
        // Retrieve the user's cart
        Cart cart = userRepo.findCartByUserId(userId);
        // Retrieve the cart item by cart and item ID
        Optional<CartItem> getItem = itemRepo.findByCartAndId(cart, itemId);
        return getItem.orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
    }

    /**
     * Retrieves a specific cart item by user and product ID.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product.
     * @return The specified CartItem.
     */
    public CartItem getCartItemByUserIdAndProductId(int userId, int productId) {
        // Retrieve the user's cart
        Cart cart = userRepo.findCartByUserId(userId);
        // Retrieve the product by ID
        Optional<ProductDetails> optional = productRepo.findById(productId);
        ProductDetails product = optional.orElseThrow(() -> new EntityNotFoundException("Product not found"));
        // Retrieve the cart item by cart and product
        Optional<CartItem> getItem = itemRepo.findByCartAndProduct(cart, product);
        return getItem.orElseThrow(() -> new EntityNotFoundException("Cart item not found"));
    }

    /**
     * Removes a product from the user's cart.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product to remove.
     * @return The removed ProductDetails.
     * @throws EntityNotFoundException If the product is not found.
     */
    @Transactional
    public ProductDetails removeFromCart(int userId, int productId) {
        // Retrieve the user's cart
        Cart cart = userRepo.findCartByUserId(userId);

        // Retrieve the product by ID
        Optional<ProductDetails> optional = productRepo.findById(productId);

        // Throw exception if product not found
        if (!optional.isPresent()) {
            throw new EntityNotFoundException("Product not found");
        }

        // Retrieve the product
        ProductDetails product = optional.get();

        // Delete the cart item by cart and product
        itemRepo.deleteByCartAndProduct(cart, product);

        return product;
    }

    /**
     * Changes the quantity of a cart item by its ID.
     *
     * @param itemId   The ID of the cart item.
     * @param quantity The new quantity.
     * @return The updated Cart.
     */
    @Transactional
    public Cart changeQuantityByItemId(int itemId, int quantity) {
        // Retrieve the cart item by ID
        Optional<CartItem> itemOptional = itemRepo.findById(itemId);
        CartItem item = itemOptional.orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        // If quantity is less than or equal to 0, delete the cart item
        if (quantity <= 0) {
            itemRepo.deleteById(itemId);
        } else {
            // Update the quantity
            item.setQuantity(quantity);
        }

        // Retrieve the cart associated with the item
        Cart cart = item.getCart();
        return cart;
    }

    /**
     * Changes the quantity of a cart item by user and product ID.
     *
     * @param userId    The ID of the user.
     * @param productId The ID of the product.
     * @param quantity  The new quantity.
     * @return The updated Cart.
     */
    @Transactional
    public Cart changeQuantityByUserIdAndProductId(int userId, int productId, int quantity) {
        // Retrieve the user's cart by ID
        Cart cart = userRepo.findCartByUserId(userId);

        // Retrieve the product by ID
        Optional<ProductDetails> optional = productRepo.findById(productId);
        ProductDetails product = optional.orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Retrieve the cart item by cart and product
        Optional<CartItem> itemOptional = itemRepo.findByCartAndProduct(cart, product);
        CartItem item = itemOptional.orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        // If quantity is less than or equal to 0, delete the cart item
        if (quantity <= 0) {
            itemRepo.deleteByCartAndProduct(cart, product);
        } else {
            // Update the quantity
            item.setQuantity(quantity);
        }

        return cart;
    }
}
