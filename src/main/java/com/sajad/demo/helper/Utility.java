package com.sajad.demo.helper;

import com.sajad.demo.domain.Product;

public class Utility {

    /**
     * Checks if the commenting rules are violated, e.g., commenting to a non-visible product,
     * or commenting to a product that is not commentable or is commentable to just buyers, and the principal
     * has not bought it previously.
     *
     * @param product          Product to check its commenting rules.
     * @param principalIsBuyer Either the principal is the previous buyer of this product.
     * @return {@code true} if there is no commenting rules violations.
     */
    public static boolean isCommentingRulesViolated(Product product, boolean principalIsBuyer) {
        return !product.isVisible() || !product.isCommentable() || (!product.isCommentableToPublic() && !principalIsBuyer);
    }

    /**
     * Checks if the rating rules are violated, e.g., rating to a non-visible product,
     * or rating to a product that is not ratable or is ratable to just buyers, and the principal
     * has not bought it previously.
     * ( I make these two methods distinct, since there may be some Rate|comment specific checks in the future as well.)
     *
     * @param product          Product to check its rating rules.
     * @param principalIsBuyer Either the principal is the previous buyer of this product.
     * @return {@code true} if there is no rating rules violations.
     */
    public static boolean isRatingRulesViolated(Product product, boolean principalIsBuyer) {
        return !product.isVisible() || !product.isRatable() || (!product.isRatableToPublic() && !principalIsBuyer);
    }
}
