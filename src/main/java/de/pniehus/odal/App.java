package de.pniehus.odal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.pniehus.odal.GUI.OdalGui;
import de.pniehus.odal.utils.Filter;
import de.pniehus.odal.utils.filters.FileTypeFilter;
import de.pniehus.odal.utils.filters.RegexFilter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	List<Filter> filters = new ArrayList<Filter>();
		filters.add(new RegexFilter());
		filters.add(new FileTypeFilter());
		OdalGui ogui = new OdalGui(args, filters);
    }
}
