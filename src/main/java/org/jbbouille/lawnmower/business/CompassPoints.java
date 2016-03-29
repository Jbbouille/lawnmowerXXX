package org.jbbouille.lawnmower.business;

public enum CompassPoints {
    northern, eastern, southern, western;

    public CompassPoints left() {
        switch (this) {
            case northern:
                return western;
            case eastern:
                return northern;
            case southern:
                return eastern;
            case western:
                return southern;
            default:
                throw new UnsupportedOperationException("Not suppose to happen, compass point can only be northern, southern, eastern or western.");
        }
    }

    public CompassPoints right() {
        switch (this) {
            case northern:
                return eastern;
            case eastern:
                return southern;
            case southern:
                return western;
            case western:
                return northern;
            default:
                throw new UnsupportedOperationException("Not suppose to happen, compass point can only be northern, southern, eastern or western.");
        }
    }
}
