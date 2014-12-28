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
        pass2(result);
        return result;
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
                if (!books.contains(book))
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

    private static void pass2(List<Set<Book>> piles)
    {
        for (Set<Book> fromPile : piles)
        {
	    for (Set<Book> toPile : piles)
	    {
		if (fromPile.size() == 5 && toPile.size() == 3)
		{
		    for (Book book : fromPile)
		    {
			if (!toPile.contains(book))
			{
			    fromPile.remove(book);
			    toPile.add(book);
			    break;
			}
		    }
		    break;
		}
            }
        }
    }
}
