package com.sajad.demo.helper;

import com.sajad.demo.domain.Product;

public class Utility {

    /**
     * @param product
     * @param principalIsBuyer
     * @return
     */
    public static boolean isCommentingRulesViolated(Product product, boolean principalIsBuyer) {
        return !product.isVisible() || !product.isCommentable() || (!product.isCommentableToPublic() && !principalIsBuyer);
    }

    /**
     * I make these two methods distinct, since there may be some Rate|comment specific checks in the future as well.
     *
     * @param product
     * @param principalIsBuyer
     * @return
     */
    public static boolean isRatingRulesViolated(Product product, boolean principalIsBuyer) {
        return !product.isVisible() || !product.isRatable() || (!product.isRatableToPublic() && !principalIsBuyer);
    }
}
