/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2013 The International Cooperation for the Integration of 
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
 * Class represented CreditCard element.
 * 
 * @since PrintTalk 1.3
 */
public class CreditCard extends AbstractPrintTalk
{
	/** */
	public static String ELEMENT_CREDITCARD = "CreditCard";
	/** */
	public static String ATTR_AUTHORIZATION = "Authorization";
	/** */
	public static String ATTR_AUTHORIZATIONEXPIRES = "AuthorizationExpires";
	/** */
	public static String ATTR_NUMBER = "Number";
	/** */
	public static String ATTR_TYPE = "Type";
	/** */
	public static String FORMAT_YEARMONTH = "yyyy-MM";

	/**
	 * 
	 * @param theElement
	 */
	public CreditCard(KElement theElement)
	{
		super(theElement);
	}

	/**
	 * get authorization value
	 * @return
	 */
	public String getAuthorization()
	{
		return getAttribute(ATTR_AUTHORIZATION);
	}

	/**
	 * set authorization value
	 * @param s
	 */
	public void setAuthorization(String s)
	{
		setAttribute(ATTR_AUTHORIZATION, s);
	}

	/**
	 * get authorization expires value
	 * @return
	 */
	public JDFDate getAuthorizationExpires()
	{
		String s = getAttribute(ATTR_AUTHORIZATIONEXPIRES);

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
	 * set authorization expires value
	 * @param expires
	 */
	public void setAuthorizationExpires(JDFDate expires)
	{
		setAttribute(ATTR_AUTHORIZATIONEXPIRES, expires == null ? null : expires.getFormattedDateTime(FORMAT_YEARMONTH));
	}

	/**
	 * get expires value
	 * @return
	 */
	public JDFDate getExpires()
	{
		String s = getAttribute(BusinessObject.ATTR_EXPIRES);

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
		setAttribute(BusinessObject.ATTR_EXPIRES, expires == null ? null : expires.getFormattedDateTime(FORMAT_YEARMONTH));
	}

	/**
	 * get number value
	 * @return
	 */
	public String getNumber()
	{
		return getAttribute(ATTR_NUMBER);
	}

	/**
	 * set number value
	 * @param s
	 */
	public void setNumber(String s)
	{
		setAttribute(ATTR_NUMBER, s);
	}

	/**
	 * get type value
	 * @return
	 */
	public String getType()
	{
		return getAttribute(ATTR_TYPE);
	}

	/**
	 * set type value
	 * @param s
	 */
	public void setType(String s)
	{
		setAttribute(ATTR_TYPE, s);
	}

}
