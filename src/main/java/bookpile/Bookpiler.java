package bookpile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 */
public class Bookpiler
{


    public static Collection<Set<Book>> sortForMaximumDiscount(List<Book> unsortedBasket)
    {
        List<Set<Book>> result = pass1(unsortedBasket);
        return pass2(result);
    }


    public static int priceSets(Collection<Set<Book>> sortedBasket)
    {
        int price = 0;
        for (Set<Book> bookSet : sortedBasket)
        {
            int discount = 0;
            switch (bookSet.size())
            {
                case 0:
                case 1:
                    break;
                case 2:
                    discount = 5;
                    break;
                case 3:
                    discount = 10;
                    break;
                case 4:
                    discount = 20;
                    break;
                case 5:
                    discount = 25;
                    break;
                default:
                    throw new RuntimeException("Too many books in this set");
            }
            int thisSetPrice = (800 * (100 - discount) * bookSet.size()) / 100;
            price = price + thisSetPrice;
        }
        return price;
    }

    private static List<Set<Book>> pass1(List<Book> basket)
    {
        List<Set<Book>> result = new ArrayList<Set<Book>>();
        for (Book book : basket)
        {
            boolean isInPile = false;
            for (Set<Book> books : result)
            {
                if (!books.contains(book) && books.size() < 4)
                {
                    books.add(book);
                    isInPile = true;
                    break;
                }
            }
            if (!isInPile)
            {
                result.add(EnumSet.of(book));
            }
        }

        return result;
    }
    private static Collection<Set<Book>> pass2(List<Set<Book>> piles)
    {
        List<Set<Book>> result = new ArrayList<Set<Book>>();
        for (Set<Book> pile : piles)
        {
            if (pile.size() == 1)
            {
                boolean addedToPile = false;
                for (Set<Book> books : result)
                {
                    if (!books.containsAll(pile)) {
                        books.addAll(pile);
                        addedToPile = true;
                        break;
                    }
                }
                if (!addedToPile){
                    result.add(pile);
                }
            }
            else
            {
                result.add(pile);
            }
        }
        return result;
    }
}


