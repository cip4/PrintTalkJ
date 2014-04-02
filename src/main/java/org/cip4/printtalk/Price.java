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

import java.util.Currency;
import java.util.Locale;
import java.util.Vector;

import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.util.NumberFormatter;
import org.cip4.jdflib.util.StringUtil;

/**
 * 
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class Price extends AbstractPrintTalk
{
	/** */
	public static String ELEMENT_PRICE = "Price";
	/** */
	public static String ATTR_PRICE = "Price";
	/** */
	public static String ATTR_UNITPRICE = "UnitPrice";
	/** */
	public static String ATTR_AMOUNT = "Amount";
	/** */
	public static String ATTR_LINEID = "LineID";
	/** */
	public static String ATTR_LINEIDREFS = "LineIDRefs";
	/** */
	public static String ATTR_PRICETYPE = "PriceType";

	static int currencyPrecision = Currency.getInstance(Locale.getDefault()).getDefaultFractionDigits();

	/**
	 *  PrintTalk 1.5 priceType enum
	 *  
	 * @author rainer prosi
	 * @date Apr 1, 2014
	 */
	public enum EnumPriceType
	{
		Discount, Markup, Product, Subtotal, Tax, TotalGross, TotalNet, TotalTax
	};

	/**
	 * 
	 *  
	 * @return
	 */
	public EnumPriceType getPriceType()
	{
		String s = theElement.getAttribute(ATTR_PRICETYPE, null, null);
		try
		{
			return s == null ? null : EnumPriceType.valueOf(s);
		}
		catch (Exception x)
		{
			return null;
		}
	}

	/**
	 * 
	 *  
	 * @param priceType 
	 * 
	 */
	public void setPriceType(EnumPriceType priceType)
	{
		theElement.setAttribute(ATTR_PRICETYPE, priceType == null ? null : priceType.name(), null);
	}

	/**
	 * 
	 * get the precision for currency
	 * @return
	 */
	public static int getCurrencyPrecision()
	{
		return currencyPrecision;
	}

	/**
	 * 
	 * set the precision for currency
	 * @param currencyPrecision typically 0 or 2 , default=2
	 */
	public static void setCurrencyPrecision(int currencyPrecision)
	{
		Price.currencyPrecision = currencyPrecision;
	}

	/**
	 * 
	 * @param theElement
	 */
	public Price(KElement theElement)
	{
		super(theElement);
	}

	/**
	 * 
	 * get the parent Pricing
	 * @return
	 */
	public Pricing getParentPricing()
	{
		return theElement.getParentNode_KElement() == null ? null : new Pricing(theElement.getParentNode_KElement());
	}

	/**
	 * set the price
	 * @param price
	 */
	public void setPrice(double price)
	{
		String amount = new NumberFormatter().formatDouble(price, currencyPrecision);
		setAttribute(ATTR_PRICE, amount);
	}

	/**
	 * get the price
	 * @return price
	 */
	public double getPrice()
	{
		return theElement.getRealAttribute(ATTR_PRICE, null, 0.0);
	}

	/**
	 * set the price per unit
	 * @param price
	 */
	public void setUnitPrice(double price)
	{
		String amount = new NumberFormatter().formatDouble(price, currencyPrecision);
		setAttribute(ATTR_UNITPRICE, amount);
	}

	/**
	 * get the price per unit
	 * @return price
	 */
	public double getUnitPrice()
	{
		return theElement.getRealAttribute(ATTR_UNITPRICE, null, 0.0);
	}

	/**
	 * set amount
	 * @param price
	 */
	public void setAmount(double price)
	{
		String amount = new NumberFormatter().formatDouble(price);
		setAttribute(ATTR_AMOUNT, amount);
	}

	/**
	 * get amount
	 * @return price
	 */
	public double getAmount()
	{
		return theElement.getRealAttribute(ATTR_AMOUNT, null, 0.0);
	}

	/**
	 *  
	 * @param lineID
	 */
	public void setLineID(String lineID)
	{
		theElement.setAttribute(ATTR_LINEID, lineID);
	}

	/**
	 * if true this price is referenced e.g. from a total and need not be included
	 * 
	 *  
	 * 
	 * @return the price, null if it doesn't exist
	 */
	public boolean isReferenced()
	{
		String lineID = getLineID();
		if (lineID == null)
		{
			return false;
		}
		Pricing parent = getParentPricing();
		if (parent == null)
		{
			return false;
		}
		Vector<Price> v = parent.getPriceVector();
		if (v == null)
		{
			log.error("whazzup - my parent ain't got me...");
			return false;
		}
		for (Price p : v)
		{
			if (equals(p))
			{
				continue;
			}
			if (p.getLineIDRefs().contains(lineID))
			{
				return true; // one is enough
			}
		}
		return false;
	}

	/**
	 *  
	 * @param lineIDRefs
	 */
	public void setLineIDRefs(VString lineIDRefs)
	{
		theElement.setAttribute(ATTR_LINEIDREFS, lineIDRefs, null);
	}

	/**
	 *  
	 * @param lineIDRef
	 */
	public void addLineIDRef(String lineIDRef)
	{
		theElement.appendAttribute(ATTR_LINEIDREFS, lineIDRef, null, " ", true);
	}

	/**
	 *  add the LineID of subPrice to LineIDRefs of this, ignoring duplicates
	 * @param subPrice
	 */
	public void refPrice(Price subPrice)
	{
		if (subPrice == null)
			return;
		VString v = getLineIDRefs();
		v.appendUnique(subPrice.getLineID());
		theElement.setAttribute(ATTR_LINEIDREFS, v, null);
	}

	/**
	 * get the list of LineIDRefs
	 * @return
	 */
	public VString getLineIDRefs()
	{
		String s = theElement.getAttribute(ATTR_LINEIDREFS, null, null);
		return StringUtil.tokenize(s, null, false);
	}

	/**
	 *  
	 * @return lineID
	 */
	public String getLineID()
	{
		return theElement.getAttribute(ATTR_LINEID, null, null);
	}

	/**
	 * add a price element
	 *
	 * @param amount
	 * @param price
	 * @return
	 */
	public Additional addAdditional(double amount, double price)
	{
		Additional a = new Additional(theElement.appendElement(Additional.ELEMENT_Additional));
		a.setAmount(amount);
		a.setPrice(price);
		return a;
	}

	/**
	 * get a price element by LineID
	 *
	 * @param i index
	 * 
	 * @return the price, null if it doesn't exist
	 */
	public Additional getAdditional(int i)
	{
		KElement a = theElement.getElement(Additional.ELEMENT_Additional, null, i);
		return a == null ? null : new Additional(a);
	}

}
