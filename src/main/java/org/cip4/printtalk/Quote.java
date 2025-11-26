/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2018 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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

import java.util.Vector;

import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.jdflib.util.StringUtil;

/**
 * Class represented Quote element.
 *
 * @since PrintTalk 1.3
 */
public class Quote extends AbstractPrintTalk
{
	/** */
	public final static String ELEMENT_QUOTE = "Quote";
	/** */
	public final static String ATTR_ESTIMATE = "Estimate";
	/** */
	public final static String ATTR_QUOTEID = "QuoteID";
	/**
	 * @deprecated
	 * */
	@Deprecated
	public final static String ATTR_REPLACEID = "ReplaceID";
	public final static String ATTR_DEVIATIONS = "Deviations";
	public final static String ATTR_RETURNJDF = "ReturnJDF";

	/**
	 *
	 * @param theElement
	 */
	public Quote(final KElement theElement)
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
		final Pricing p = getPricing();
		return p == null ? null : p.getCurrency();
	}

	/**
	 * set currency value
	 *
	 * @param currency
	 */
	public void setCurrency(final String currency)
	{
		getCreatePricing().setCurrency(currency);
	}

	/**
	 * get estimate value
	 *
	 * @return
	 */
	public boolean getEstimate()
	{
		return getAttribute(ATTR_ESTIMATE).equalsIgnoreCase("true") ? true : false;
	}

	/**
	 * set estimate value
	 *
	 * @param b
	 */
	public void setEstimate(final boolean b)
	{
		setAttribute(ATTR_ESTIMATE, b ? "true" : "false");
	}

	/**
	 * get quote id value
	 *
	 * @return
	 */
	public String getQuoteID()
	{
		return getAttribute(ATTR_QUOTEID);
	}

	/**
	 * set quote id value
	 *
	 * @param s
	 */
	public void setQuoteID(final String s)
	{
		setAttribute(ATTR_QUOTEID, s);
	}

	/**
	 * get replace id value
	 *
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public String getReplaceID()
	{
		return getAttribute(ATTR_REPLACEID);
	}

	/**
	 * set replace id value
	 *
	 * @param s
	 * @deprecated
	 */
	@Deprecated
	public void setReplaceID(final String s)
	{
		setAttribute(ATTR_REPLACEID, s);
	}

	/**
	 * get return jdf value
	 *
	 * @return
	 */
	public boolean getReturnJDF()
	{
		return getAttribute(ATTR_RETURNJDF).equalsIgnoreCase("true") ? true : false;
	}

	/**
	 * set return jdf value
	 *
	 * @param b
	 */
	public void setReturnJDF(final boolean b)
	{
		setAttribute(ATTR_RETURNJDF, b ? "true" : "false");
	}

	/**
	 * create pricing element
	 *
	 * @return
	 */
	public Pricing getCreatePricing()
	{
		return new Pricing(getCreateElement(Pricing.ELEMENT_PRICING));
	}

	/**
	 * get pricing element
	 *
	 * @return
	 */
	public Pricing getPricing()
	{
		return new Pricing(getElement(Pricing.ELEMENT_PRICING));
	}

	/**
	 * @see AbstractPrintTalk#setXJDF(XJDFHelper)
	 */
	@Override
	public void setXJDF(final XJDFHelper xjdf)
	{
		super.setXJDF(xjdf);
	}

	/**
	 * @see AbstractPrintTalk#getXJDF(int)
	 */
	@Override
	public XJDFHelper getXJDF(final int i)
	{
		return super.getXJDF(i);
	}

	/**
	 * @see AbstractPrintTalk#getXJDFs()
	 */
	@Override
	public Vector<XJDFHelper> getXJDFs()
	{
		return super.getXJDFs();
	}

	/**
	 * @see AbstractPrintTalk#appendXJDF(XJDFHelper)
	 */
	@Override
	public void appendXJDF(final XJDFHelper xjdf)
	{
		super.appendXJDF(xjdf);
	}

	/**
	 *
	 * @param deviations
	 */
	public void setDeviations(final VString deviations)
	{
		final String val = StringUtil.setvString(deviations);
		setAttribute(ATTR_DEVIATIONS, val);
	}

	/**
	 *
	 * @return
	 */
	public VString getDeviations()
	{
		return StringUtil.tokenize(getAttribute(ATTR_DEVIATIONS), null, false);
	}

}
