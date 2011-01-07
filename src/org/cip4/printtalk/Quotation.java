/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2009 The International Cooperation for the Integration of 
 * Processes in  Prepress, Press and Postpress (CIP4).  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        The International Cooperation for the Integration of 
 *        Processes in  Prepress, Press and Postpress (www.cip4.org)"
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of 
 *    Processes in  Prepress, Press and Postpress" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4",
 *    nor may "CIP4" appear in their name, without prior written
 *    permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For
 * details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR
 * THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the The International Cooperation for the Integration 
 * of Processes in Prepress, Press and Postpress and was
 * originally based on software 
 * copyright (c) 1999-2001, Heidelberger Druckmaschinen AG 
 * copyright (c) 1999-2001, Agfa-Gevaert N.V. 
 *  
 * For more information on The International Cooperation for the 
 * Integration of Processes in  Prepress, Press and Postpress , please see
 * <http://www.cip4.org/>.
 *  
 * 
 */
package org.cip4.printtalk;

import java.util.zip.DataFormatException;

import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.util.JDFDate;

/**
 * Class represented Quotation.
 * 
 * @author rainer prosi
 * @date Jan 3, 2011
 * @since PrintTalk 1.3
 */
public class Quotation extends BusinessObject
{
	public static String ATTR_CURRENCY = "Currency";
	public static String ATTR_ESTIMATE = "Estimate";
	public static String ATTR_EXPIRES = "Expires";
	public static String ATTR_REORDERID = "ReorderID";
	public static String ATTR_REPLACEID = "ReplaceID";

	/**
	 * 
	 * @param theElement
	 */
	public Quotation(KElement theElement)
	{
		super(theElement);
	}

	/**
	 * get currency value
	 * @return
	 */
	public String getCurrency()
	{
		return getAttribute(ATTR_CURRENCY);
	}

	/**
	 * set currency value
	 * @param currency
	 */
	public void setCurrency(String currency)
	{
		setAttribute(ATTR_CURRENCY, currency);
	}

	/**
	 * get estimate value
	 * @return
	 */
	public boolean getEstimate()
	{
		return getAttribute(ATTR_ESTIMATE).equalsIgnoreCase("true") ? true : false;
	}

	/**
	 * set estimate value
	 * @param b
	 */
	public void setEstimate(boolean b)
	{
		setAttribute(ATTR_ESTIMATE, b ? "true" : "false");
	}

	/**
	 * get expires value
	 * @return
	 */
	public JDFDate getExpires()
	{
		String s = getAttribute(ATTR_EXPIRES);
		try
		{
			return (s == null) ? null : new JDFDate(s);
		}
		catch (DataFormatException e)
		{
			return null;
		}
	}

	/**
	 * set expires value
	 * @param expires
	 */
	public void setExpires(JDFDate expires)
	{
		setAttribute(ATTR_EXPIRES, expires == null ? null : expires.getDateTimeISO());
	}

	/**
	 * get ReorderID value
	 * @return
	 */
	public String getReorderID()
	{
		return getAttribute(ATTR_REORDERID);
	}

	/**
	 * set ReorderID value
	 * @param s
	 */
	public void setReorderID(String s)
	{
		setAttribute(ATTR_REORDERID, s);
	}

	/**
	 * get ReplaceID value
	 * @return
	 */
	public String getReplaceID()
	{
		return getAttribute(ATTR_REPLACEID);
	}

	/**
	 * set ReplaceID value
	 * @param s
	 */
	public void setReplaceID(String s)
	{
		setAttribute(ATTR_REPLACEID, s);
	}

}
