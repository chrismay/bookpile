package bookpile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static bookpile.Book.A;
import static bookpile.Book.B;
import static bookpile.Book.C;
import static bookpile.Book.D;
import static bookpile.Book.E;

public class BookPricingTest
    extends TestCase
{
    private static List<Book> PROVIDED_INPUT = Arrays.asList(A, A, B, B, C, C, D, E);

    public BookPricingTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(BookPricingTest.class);
    }

    public void testProvidedCase()
    {
        Collection<Set<Book>> sortedBasket = Bookpiler.sortForMaximumDiscount(PROVIDED_INPUT);
        int price = Bookpiler.priceSets(sortedBasket);
        assertEquals(5120, price);
    }


    public void testSortTrivialCase(){
        List<Book> threeDifferentBooks = Arrays.asList(A, B, C);
        Collection<Set<Book>> sorted = Bookpiler.sortForMaximumDiscount(threeDifferentBooks);

        assertResultMatches(Arrays.asList(EnumSet.of(A, B, C)), sorted);
    }

    public void testSortTrivialCase2(){
        List<Book> twoIdenticalBooks = Arrays.asList(A, A);
        Collection<Set<Book>> sorted = Bookpiler.sortForMaximumDiscount(twoIdenticalBooks);

        assertResultMatches(Arrays.asList(EnumSet.of(A), EnumSet.of(A)), sorted);
    }

    public void testSortTrivialCase3(){
        List<Book> fullSet = Arrays.asList(A, B, C, D, E);
        Collection<Set<Book>> sorted = Bookpiler.sortForMaximumDiscount(fullSet);

        assertResultMatches(Arrays.asList(EnumSet.of(A, B, C, D, E)), sorted);
    }

    public void testHandlesSingleStrayCorrectly(){
        List<Book> fullSet = Arrays.asList(A, B, C, D, A);
        Collection<Set<Book>> sorted = Bookpiler.sortForMaximumDiscount(fullSet);

        assertResultMatches(Arrays.asList(EnumSet.of(A, B, C, D), EnumSet.of(A)), sorted);
    }

    public void testSortProvidedCase(){
        List<Book> providedBooks = Arrays.asList(A, A, B, B, C, C, D, E);
        Collection<Set<Book>> sorted = Bookpiler.sortForMaximumDiscount(providedBooks);

        assertResultMatches(Arrays.asList(EnumSet.of(A, B, C, D), EnumSet.of(A, B, C, E)), sorted);
    }

    public void test4Sets(){
        List<Book> providedBooks = Arrays.asList(
            B, C, D, E,
            A, C, D, E,
            A, B, D, E,
            A, B, C, E,
            A, B, C, D);
        Collection<Set<Book>> sorted = Bookpiler.sortForMaximumDiscount(providedBooks);

        assertResultMatches(Arrays.asList(EnumSet.of(A, B, C, D, E), EnumSet.of(A, B, C, D, E), EnumSet.of(A, B, C, D, E), EnumSet.of(A, B, C, D, E)), sorted);
    }

    private void assertResultMatches(List<EnumSet<Book>> expected, Collection<Set<Book>> actual)
    {
        List<EnumSet<Book>> unmatched = new ArrayList<EnumSet<Book>>(expected);
        assertEquals(expected.size(), actual.size());
        for (Set<Book> books : actual)
        {
            assertTrue(unmatched.remove(books));
        }
        assertTrue(unmatched.isEmpty());
    }

    public void testPricingSet()
    {
        Set<Book> none = EnumSet.noneOf(Book.class);
        Set<Book> one = EnumSet.of(A);
        Set<Book> two = EnumSet.of(A, B);
        Set<Book> three = EnumSet.of(A, B, C);
        Set<Book> four = EnumSet.of(A, B, C, D);
        Set<Book> five = EnumSet.of(A, B, C, D, E);
        assertEquals(0, Bookpiler.priceSets(Collections.singletonList(none)));
        assertEquals(800, Bookpiler.priceSets(Collections.singletonList(one)));
        assertEquals(1520, Bookpiler.priceSets(Collections.singletonList(two)));
        assertEquals(2160, Bookpiler.priceSets(Collections.singletonList(three)));
        assertEquals(2560, Bookpiler.priceSets(Collections.singletonList(four)));
        assertEquals(3000, Bookpiler.priceSets(Collections.singletonList(five)));

        assertEquals(3680, Bookpiler.priceSets(Arrays.asList(two, three)));
        assertEquals(5120, Bookpiler.priceSets(Arrays.asList(four, four)));
    }
}
