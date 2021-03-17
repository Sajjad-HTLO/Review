package com.sajad.demo.dto;

import javax.validation.constraints.NotNull;

public class ProductUpdateDto {

    @NotNull
    private boolean isVisible;

    @NotNull
    private boolean commentableToPublic;

    @NotNull
    private boolean commentableToBuyers;

    @NotNull
    private boolean votableToPublic;

    @NotNull
    private boolean votableToBuyers;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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

    public boolean isVotableToPublic() {
        return votableToPublic;
    }

    public void setVotableToPublic(boolean votableToPublic) {
        this.votableToPublic = votableToPublic;
    }

    public boolean isVotableToBuyers() {
        return votableToBuyers;
    }

    public void setVotableToBuyers(boolean votableToBuyers) {
        this.votableToBuyers = votableToBuyers;
    }
}
