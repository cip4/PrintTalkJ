/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2017 The International Cooperation for the Integration of
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

import java.util.Vector;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VElement;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.util.StringUtil;
import org.cip4.printtalk.Price.EnumTaxType;

/**
 * Class represented Pricing element.
 *
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class Pricing extends AbstractPrintTalk
{
	/** */
	public final static String ELEMENT_PRICING = "Pricing";

	/**
	 *
	 * @param theElement
	 */
	public Pricing(final KElement theElement)
	{
		super(theElement);
	}

	/**
	 * create payment element
	 * @return
	 */
	public Payment getCreatePayment()
	{
		return new Payment(getCreateElement(Payment.ELEMENT_PAYMENT));
	}

	/**
	 * get payment element
	 * @return
	 */
	public Payment getPayment()
	{
		final KElement e = getElement(Payment.ELEMENT_PAYMENT);
		return e == null ? null : new Payment(e);
	}

	/**
	 * add a price element
	 *
	 * @param description
	 * @param totalprice
	 * @return
	 */
	public Price addPrice(final String description, final double totalprice)
	{
		final Price price = new Price(theElement.appendElement(Price.ELEMENT_PRICE));
		price.setDescriptiveName(description);
		price.setLineID("L_" + theElement.numChildElements(Price.ELEMENT_PRICE, null));
		if (totalprice >= 0)
			price.setPrice(totalprice);
		return price;
	}

	/**
	 * get a price element by LineID
	 *
	 * @param lineID
	 *
	 * @return the price, null if it doesn't exist
	 */
	public Price getPrice(final String lineID)
	{
		final KElement price = theElement == null ? null : theElement.getChildWithAttribute(Price.ELEMENT_PRICE, Price.ATTR_LINEID, null, lineID, 0, true);
		return price == null ? null : new Price(price);
	}

	/**
	 * get a price element by DescriptiveName
	 *
	 * @param desc
	 *
	 * @return the price, null if it doesn't exist
	 */
	public Price getPriceByDescription(final String desc)
	{
		final KElement price = theElement == null ? null : theElement.getChildWithAttribute(Price.ELEMENT_PRICE, AttributeName.DESCRIPTIVENAME, null, desc, 0, true);
		return price == null ? null : new Price(price);
	}

	/**
	 *
	 * @param typ
	 * @param taxtype
	 * @param i
	 * @deprecated use 4 parameter version
	 * @return
	 */
	@Deprecated
	public Price getPriceByType(final String typ, final EnumTaxType taxtype, final int i)
	{
		return getPriceByType(typ, taxtype, null, i);
	}

	/**
	 *
	 */
	public Price getPriceByType(final String typ, final EnumTaxType taxtype, final String dropID, int i)
	{
		final Vector<Price> v = getPricesByType(typ, taxtype, dropID);
		if (v == null)
			return null;
		if (i < 0)
			i = v.size() + i;
		return (i >= 0 && i < v.size()) ? v.get(i) : null;
	}

	/**
	 *
	 */
	public Vector<Price> getPricesByType(final String typ, final EnumTaxType taxtype, final String dropID)
	{
		final JDFAttributeMap map = new JDFAttributeMap();
		if (!StringUtil.isEmpty(typ))
			map.put(Price.ATTR_PRICETYPE, typ);
		if (taxtype != null)
			map.put(Price.ATTR_TAXTYPE, taxtype.name());
		if (!StringUtil.isEmpty(dropID))
			map.put(AttributeName.DROPID, dropID);

		final VElement v = theElement == null ? null : theElement.getChildElementVector(Price.ELEMENT_PRICE, null, map, true, 0, false);
		if (v == null || v.isEmpty())
		{
			return null;
		}
		else
		{
			final Vector<Price> ret = new Vector<>();
			for (final KElement e : v)
			{
				ret.add(new Price(e));
			}
			return ret;
		}
	}

	/**
	 *
	 * get a vector of all child Price elements
	 * @return
	 */
	public Vector<Price> getPriceVector()
	{
		if (theElement == null)
			return null;

		final Vector<Price> v = new Vector<Price>();
		final VElement prices = theElement.getChildElementVector(Price.ELEMENT_PRICE, null);
		for (final KElement price : prices)
		{
			v.add(new Price(price));
		}
		return v.isEmpty() ? null : v;
	}

	/**
	 *
	 * get a vector of all child Price elements
	 * @return
	 * @deprecated use getPricesByType getPricesByType
	 */
	@Deprecated
	public Vector<Price> getPricesForDrop(final String dropID)
	{
		return getPricesByType(null, null, dropID);
	}

}
