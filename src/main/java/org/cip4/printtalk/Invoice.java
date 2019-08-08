/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2017 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution, if any, must include the following acknowledgment: "This product includes software developed by the The International Cooperation for
 * the Integration of Processes in Prepress, Press and Postpress (www.cip4.org)" Alternately, this acknowledgment may appear in the software itself, if and wherever such third-party acknowledgments
 * normally appear.
 *
 * 4. The names "CIP4" and "The International Cooperation for the Integration of Processes in Prepress, Press and Postpress" must not be used to endorse or promote products derived from this software
 * without prior written permission. For written permission, please contact info@cip4.org.
 *
 * 5. Products derived from this software may not be called "CIP4", nor may "CIP4" appear in their name, without prior written permission of the CIP4 organization
 *
 * Usage of this software in commercial products is subject to restrictions. For details please consult info@cip4.org.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE INTERNATIONAL COOPERATION FOR THE INTEGRATION OF PROCESSES IN PREPRESS, PRESS AND POSTPRESS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE. ====================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the The International Cooperation for the Integration of Processes in Prepress, Press and Postpress and was
 * originally based on software copyright (c) 1999-2001, Heidelberger Druckmaschinen AG copyright (c) 1999-2001, Agfa-Gevaert N.V.
 *
 * For more information on The International Cooperation for the Integration of Processes in Prepress, Press and Postpress , please see <http://www.cip4.org/>.
 *
 *
 */
package org.cip4.printtalk;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.util.JDFDate;

/**
 * Class represented Invoice business object.
 *
 * @author rainer prosi
 * @date Jan 3, 2011
 * @since PrintTalk 1.3
 */
public class Invoice extends BusinessObject
{
	/** */
	public final static String ELEMENT_PRICING = "Pricing";

	/**
	 * get or create MasterContract element
	 *
	 * @param contractID the new contractID
	 * @return
	 */
	@Override
	public MasterContract getCreateMasterContract(final String contractID)
	{
		return super.getCreateMasterContract(contractID);
	}

	/**
	 * get the existing MasterContract element
	 *
	 *
	 * @return
	 */
	@Override
	public MasterContract getMasterContract()
	{
		return super.getMasterContract();
	}

	/**
	 *
	 * @param theElement
	 */
	public Invoice(final KElement theElement)
	{
		super(theElement);
	}

	/**
	 * get currency value
	 *
	 * @return
	 */
	public String getCurrency()
	{
		return getAttribute(ATTR_CURRENCY);
	}

	/**
	 * set currency value
	 *
	 * @param currency
	 */
	public void setCurrency(final String currency)
	{
		setAttribute(ATTR_CURRENCY, currency == null ? null : currency.toUpperCase());
	}

	/**
	 *
	 * @see org.cip4.printtalk.AbstractPrintTalk#appendXJDF(org.cip4.jdflib.extensions.XJDFHelper)
	 */
	@Override
	public void appendXJDF(final XJDFHelper xjdf)
	{
		super.appendXJDF(xjdf);
	}

	/**
	 * get expires value
	 *
	 * @return
	 * @deprecated use getDueDate
	 */
	@Deprecated
	public JDFDate getExpires()
	{
		return getDueDate();
	}

	/**
	 * get expires value
	 *
	 * @return
	 */
	public JDFDate getDueDate()
	{
		final String s = getAttribute(AttributeName.DUEDATE);
		final JDFDate d = JDFDate.createDate(s);
		return d == null ? JDFDate.createDate(getAttribute(ATTR_EXPIRES)) : d;
	}

	/**
	 * set expires value
	 *
	 * @param expires
	 */
	public void setDueDate(final JDFDate expires)
	{
		setAttribute(AttributeName.DUEDATE, expires == null ? null : expires.getDateTimeISO());
	}

	/**
	 * set expires value
	 *
	 * @param expires
	 * @deprecated use setDueDate
	 */
	@Deprecated
	public void setExpires(final JDFDate expires)
	{
		setDueDate(expires);
	}

	/**
	 * set the expires dates
	 *
	 * @param i
	 */
	public void setDueDate(final int days)
	{
		setDueDate(getExpirationDays(days));
	}

	/**
	 * create pricing element
	 *
	 * @return
	 */
	public Pricing getCreatePricing()
	{
		return new Pricing(getCreateElement(ELEMENT_PRICING));
	}

	/**
	 * get pricing element
	 *
	 * @return
	 */
	public Pricing getPricing()
	{
		return new Pricing(getElement(ELEMENT_PRICING));
	}

	/**
	 * @see org.cip4.printtalk.AbstractPrintTalk#cleanUp()
	 */
	@Override
	public void cleanUp()
	{
		final Pricing p = getPricing();
		if (p != null)
			p.cleanUp();
		super.cleanUp();
	}

}
