package com.project.rc.task2;

import java.util.Objects;

public class Token {
    private static Token instance;
    private String sourceIp;
    private String destinationIp;
    private String message;
    private boolean isFree;
    private boolean reachedDestination;

    private Token() {
        this.sourceIp = null;
        this.destinationIp = null;
        this.message = null;
        this.isFree = true;
        this.reachedDestination = false;
    }

    public static Token getInstance() {
        if (instance == null) {
            instance = new Token();
        }
        return instance;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean getReachedDestination() {
        return reachedDestination;
    }

    public void setReachedDestination(boolean reachedDestination) {
        this.reachedDestination = reachedDestination;
    }

    @Override
    public String toString() {
        return "Token{" +
              "sourceIp='" + sourceIp + '\'' +
              ", destinationIp='" + destinationIp + '\'' +
              ", message='" + message + '\'' +
              ", isFree=" + isFree +
              ", reachedDestination=" + reachedDestination +
              '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(sourceIp, token.sourceIp) &&
              Objects.equals(destinationIp, token.destinationIp) &&
              Objects.equals(message, token.message) &&
              Objects.equals(isFree, token.isFree) &&
              Objects.equals(reachedDestination, token.reachedDestination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceIp, destinationIp, message, isFree, reachedDestination);
    }
}
