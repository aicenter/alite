package cz.agents.alite.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.Assert;

import org.junit.Test;

public class TestCartesianProduct {

    @Test
    public void test() {
        List<Set<String>> definitionsOfDomains = new ArrayList<Set<String>>();
        definitionsOfDomains.add(new TreeSet<String>(Arrays.asList("A", "B", "C")));
        definitionsOfDomains.add(new TreeSet<String>(Arrays.asList("1", "2")));
        definitionsOfDomains.add(new TreeSet<String>(Arrays.asList("X", "Y", "Z")));

        CartesianProduct<String> cartesianProduct = new CartesianProduct<String>(definitionsOfDomains);

        Set<List<String>> product = new HashSet<List<String>>();
        int size = cartesianProduct.size();
        for (int i = 0; i < size; i++) {
            product.add(cartesianProduct.element(i));
        }

        Assert.assertEquals(18, size);

        Assert.assertTrue(product.contains(Arrays.asList("A", "1", "X")));
        Assert.assertTrue(product.contains(Arrays.asList("A", "1", "Y")));
        Assert.assertTrue(product.contains(Arrays.asList("A", "1", "Z")));
        Assert.assertTrue(product.contains(Arrays.asList("A", "2", "X")));
        Assert.assertTrue(product.contains(Arrays.asList("A", "2", "Y")));
        Assert.assertTrue(product.contains(Arrays.asList("A", "2", "Z")));
        Assert.assertTrue(product.contains(Arrays.asList("B", "1", "X")));
        Assert.assertTrue(product.contains(Arrays.asList("B", "1", "Y")));
        Assert.assertTrue(product.contains(Arrays.asList("B", "1", "Z")));
        Assert.assertTrue(product.contains(Arrays.asList("B", "2", "X")));
        Assert.assertTrue(product.contains(Arrays.asList("B", "2", "Y")));
        Assert.assertTrue(product.contains(Arrays.asList("B", "2", "Z")));
        Assert.assertTrue(product.contains(Arrays.asList("C", "1", "X")));
        Assert.assertTrue(product.contains(Arrays.asList("C", "1", "Y")));
        Assert.assertTrue(product.contains(Arrays.asList("C", "1", "Z")));
        Assert.assertTrue(product.contains(Arrays.asList("C", "2", "X")));
        Assert.assertTrue(product.contains(Arrays.asList("C", "2", "Y")));
        Assert.assertTrue(product.contains(Arrays.asList("C", "2", "Z")));
    }

}
