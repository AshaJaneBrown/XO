package xo.controllers;

import xo.model.Field;
import xo.model.Figure;
import xo.model.exceptions.AlreadyOccupiedException;
import xo.model.exceptions.InvalidPointException;

import java.awt.*;

public class MoveController {

    public void applyFigure(final Field field,
                            final Point point,
                            final Figure figure) throws InvalidPointException,
                                                        AlreadyOccupiedException {
        if (field.getFigure(point )!= null) {
            throw new AlreadyOccupiedException();
        }
        field.setFigure(point, figure);
    }
}
