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
package cz.cvut.fel.aic.alite.math;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Cartesian product generator.
 *
 * TODO: iterable
 * TODO: memoizing
 *
 * @param <E> element type
 */
public class CartesianProduct<E> {

    private final List<List<E>> domains = new ArrayList<List<E>>();

    /**
     * Constructs a Cartesian product generator.
     *
     * @param definitionsOfDomains the inner set represent domain of i-th variable
     */
    public CartesianProduct(List<Set<E>> definitionsOfDomains) {
        for (Set<E> set : definitionsOfDomains) {
            domains.add(new ArrayList<E>(set));
        }
    }

    /**
     * Returns i-th element of the Cartesian product based on the domain definition.
     *
     * For all {@code i >= size()} the method returns {@code null};
     *
     * @param i index
     * @return i-th particular element of the Cartesian product
     */
    public List<E> element(int i) {
        LinkedList<E> result = new LinkedList<E>();

        for (List<E> currentDomain : domains) {
            int currentSize = currentDomain.size();

            int currentIndex = i % currentSize;
            i /= currentSize;

            result.add(currentDomain.get(currentIndex));
        }

        if (i > 0) {
            // index overflow => stop generating
            result = null;
        }

        return result;
    }

    /**
     * Returns the size of the Cartesian product, i.e., number of elements.
     *
     * @return size of the Cartesian product
     */
    public int size() {
        int result = 0;

        for (List<E> currentDomain : domains) {
            int currentSize = currentDomain.size();
            if (result == 0) {
                result = currentSize;
            } else {
                result *= currentSize;
            }
        }

        return result;
    }

}
