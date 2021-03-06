/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.sword;

import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import org.dspace.core.Context;
import org.dspace.content.BitstreamFormat;

import org.purl.sword.base.Deposit;

/**
 * @author Richard Jones
 *
 * This class represents the actual sword service provided by dspace.  It
 * is the central location for the authentcated contexts, the installation
 * specific configuration, and the url management.
 *
 * It is ubiquotous in the sword implementation, and all related
 * information and services should be retrived via this api
 *
 */
public class SWORDService
{
	/** Log4j logging instance */
	public static final Logger log = Logger.getLogger(SWORDService.class);

	/** The SWORD context of the request */
	private SWORDContext swordContext;

	/** The configuration of the sword server instance */
	private SWORDConfiguration swordConfig;

	/** The URL Generator */
	private SWORDUrlManager urlManager;

	/** a holder for the messages coming through from the implementation */
	private StringBuilder verboseDescription = new StringBuilder();

	/** is this a verbose operation */
	private boolean verbose = false;

	/** date formatter */
	private SimpleDateFormat dateFormat;

	/**
	 * Construct a new service instance around the given authenticated
	 * sword context
	 *
	 * @param sc
	 */
	public SWORDService(SWORDContext sc)
	{
		this.swordContext = sc;
		this.swordConfig = new SWORDConfiguration();
		this.urlManager = new SWORDUrlManager(this.swordConfig, this.swordContext.getContext());
		dateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.S]");
	}

	public SWORDUrlManager getUrlManager()
	{
		return urlManager;
	}

	public void setUrlManager(SWORDUrlManager urlManager)
	{
		this.urlManager = urlManager;
	}

	public SWORDContext getSwordContext()
	{
		return swordContext;
	}

	public void setSwordContext(SWORDContext swordContext)
	{
		this.swordContext = swordContext;
	}

	public SWORDConfiguration getSwordConfig()
	{
		return swordConfig;
	}

	public void setSwordConfig(SWORDConfiguration swordConfig)
	{
		this.swordConfig = swordConfig;
	}

	public Context getContext()
	{
		return this.swordContext.getContext();
	}

	public boolean isVerbose()
	{
		return verbose;
	}

	public void setVerbose(boolean verbose)
	{
		this.verbose = verbose;
	}

	public StringBuilder getVerboseDescription()
	{
		return verboseDescription;
	}

	/**
	 * shortcut to registering a message with the verboseDescription
	 * member variable.  This checks to see if the request is
	 * verbose, meaning we don't have to do it inline and break nice
	 * looking code up
	 *
	 * @param message
	 */
	public void message(String message)
	{
		// build the processing message
		String msg = dateFormat.format(new Date()) + " " + message + "; \n\n";

		// if this is a verbose deposit, then log it
		if (this.verbose)
		{
			verboseDescription.append(msg);
		}

		// add to server logs anyway
		log.info(msg);
	}

	/**
	 * Construct the most appropriate filename for the incoming deposit
	 *
	 * @param context
	 * @param deposit
	 * @param original
	 * @return
	 * @throws DSpaceSWORDException
	 */
	public String getFilename(Context context, Deposit deposit, boolean original)
			throws DSpaceSWORDException
	{
		try
		{
			BitstreamFormat bf = BitstreamFormat.findByMIMEType(context, deposit.getContentType());
			String[] exts = null;
			if (bf != null)
			{
				exts = bf.getExtensions();
			}

			String fn = deposit.getFilename();
			if (fn == null || "".equals(fn))
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				fn = "sword-" + sdf.format(new Date());
				if (original)
				{
					fn = fn + ".original";
				}
				if (exts != null)
				{
					fn = fn + "." + exts[0];
				}
			}

			return fn;
		}
		catch (SQLException e)
		{
			throw new DSpaceSWORDException(e);
		}
	}

	/**
	 * Get the name of the temp files that should be used
	 * 
	 * @return
	 */
	public String getTempFilename()
	{
		return "sword-" + (new Date()).getTime();
	}
}
