package com.sajad.demo.dto.product;

import javax.validation.constraints.NotNull;

public class ProductUpdateDto {

    @NotNull
    private boolean visible;

    @NotNull
    private boolean commentableToPublic;

    @NotNull
    private boolean commentableToBuyers;

    @NotNull
    private boolean ratableToPublic;

    @NotNull
    private boolean ratableToBuyers;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isCommentableToPublic() {
        return commentableToPublic;
    }

    public void setCommentableToPublic(boolean commentableToPublic) {
        this.commentableToPublic = commentableToPublic;
    }

    public boolean isCommentableToBuyers() {
        return commentableToBuyers;
    }

    public void setCommentableToBuyers(boolean commentableToBuyers) {
        this.commentableToBuyers = commentableToBuyers;
    }

    public boolean isRatableToPublic() {
        return ratableToPublic;
    }

    public void setRatableToPublic(boolean ratableToPublic) {
        this.ratableToPublic = ratableToPublic;
    }

    public boolean isRatableToBuyers() {
        return ratableToBuyers;
    }

    public void setRatableToBuyers(boolean ratableToBuyers) {
        this.ratableToBuyers = ratableToBuyers;
    }
}
