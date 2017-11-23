/* 
 * Copyright (C) 2017 Czech Technical University in Prague.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cz.cvut.fel.aic.alite.planner.spatialmaneuver;

import java.io.PrintStream;

import javax.vecmath.Point2d;
import javax.vecmath.Vector3d;

import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.BoxZone;
import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.CylinderZone;
import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.EllipsoidZone;
import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.EmptyZone;
import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.GroupZone;
import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.PrismZone;
import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.TransformZone;
import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.Zone;
import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.ZoneVisitor;


public class DebugTextZoneVisitor implements ZoneVisitor {

    private final Vector3d translation = new Vector3d();

    private PrintStream out;

    public DebugTextZoneVisitor(PrintStream stream) {
        out = stream;
    }

    @Override
    public void visit(CylinderZone zone) {
        Point2d oldPoint = null;
        for (double angle = 0; angle <= 2.1 * Math.PI; angle += Math.PI / 32.0) {
            Point2d point = new Point2d(zone.getSemiAxes().x * Math.cos(angle),
                    zone.getSemiAxes().y * Math.sin(angle));
            if (oldPoint != null) {
                out.println((translation.x + point.x) + ", " + (translation.y + point.y) + ", 50");
            }
            oldPoint = point;
        }
    }

    @Override
    public void visit(EmptyZone zone) {
    }

    @Override
    public void visit(GroupZone zone) {
        for (Zone subZone : zone.getSubZones()) {
            subZone.accept(this);
        }
    }

    @Override
    public void visit(TransformZone zone) {
        if (zone.getAngleRotation() != 0 || zone.getSize().x != 1
                || zone.getSize().y != 1) {
            throw new RuntimeException("Not implemented!");
        }

        translation.add(zone.getTranslation());
        zone.getZone().accept(this);
        translation.sub(zone.getTranslation());
    }

    @Override
    public void visit(EllipsoidZone zone) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void visit(BoxZone zone) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void visit(PrismZone zone) {
        Point2d oldPoint = null;
        Point2d firstPoint = null;
        for (Point2d point : zone.getPoints()) {
            if (oldPoint != null) {
                out.println((translation.x + point.x) + ", " + (translation.y + point.y) + ", 50");
            } else {
                firstPoint = point;
            }
            oldPoint = point;
        }
        out.println((translation.x + firstPoint.x) + ", " + (translation.y + firstPoint.y) + ", 50");
    }

}
