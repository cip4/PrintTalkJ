/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2019 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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

import org.cip4.jdflib.core.XMLDoc;
import org.cip4.printtalk.Price.EnumPriceType;
import org.cip4.printtalk.Price.EnumTaxType;
import org.junit.Test;

/**
 *
 * @author rainer prosi
 * @date Jan 4, 2011
 */
public class PriceTest extends PrintTalkTestCase
{
	/**
	 *
	 *
	 */
	@Test
	public void testCurrency()
	{
		assertEquals("will fail in japan", 2, Price.getCurrencyPrecision());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testEquals()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice("p1", 20);
		assertEquals(p1, new Price(p1.getRoot()));
		assertEquals(p, new Price(p.getRoot()));
	}

	/**
	 *
	 *
	 */
	@Test
	public void testPriceType()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice(EnumPriceType.Total, EnumTaxType.Gross, "p1", 20);
		assertEquals(p1.getPriceTypeEnum(), EnumPriceType.Total);
		assertEquals(p1.getPriceTypeString(), EnumPriceType.Total.name());
		reparse(p1, defaultversion, false);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testPriceMissing()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice(EnumPriceType.Total, EnumTaxType.Gross, "p1", 20);
		p1.getRoot().removeAttribute(Price.ATTR_PRICE);
		reparse(p1, defaultversion, true);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testUnitInvalid()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice(EnumPriceType.Total, EnumTaxType.Gross, "p1", 20);
		p1.setAttribute("Unit", "m");
		reparse(p1, defaultversion, true);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testPriceTypeMissing()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice(null, EnumTaxType.Gross, "p1", 20);
		reparse(p1, defaultversion, true);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testTaxTypeMissing()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice(EnumPriceType.Discount, null, "p1", 20);
		reparse(p1, defaultversion, true);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testTax()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice(null, EnumTaxType.Net, "p1", 20);
		assertEquals(p1.getTaxType(), EnumTaxType.Net);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testEnsureLineID()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice(null, EnumTaxType.Net, "p1", 20);
		assertNull(p1.getLineID());
		assertNotNull(p1.ensureLineID());
	}

	void reparse(final Price p, final int ptv, final boolean fail)
	{
		final Pricing pr = new Pricing(new XMLDoc(Pricing.ELEMENT_PRICING, null).getRoot());
		pr.getRoot().copyElement(p.getRoot(), null);
		pr.cleanUp();
		PricingTest.reparse(pr, ptv, fail);
	}

	/**
	 *
	 *
	 */
	@Test
	public void testRefPrice()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice("p1", 20);
		final Price p2 = p.addPrice("p2", 22);
		p2.refPrice(p1);
		assertTrue(p2.getLineIDRefs().contains(p1.getLineID()));
		assertFalse(p2.getLineIDRefs().contains(p2.getLineID()));
	}

	/**
	 *
	 *
	 */
	@Test
	public void testIsReferenced()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		final Price p1 = p.addPrice("p1", 20);
		final Price p2 = p.addPrice("p2", 22);
		final Price p3 = p.addPrice("p3", 33);
		final Price p4 = p.addPrice(null, 33);
		p2.refPrice(p1);
		assertTrue(p1.isReferenced());
		assertFalse(p2.isReferenced());
		assertFalse(p3.isReferenced());
		assertFalse(p4.isReferenced());
	}

	/**
	 *
	 *
	 */
	@Test
	public void testAddRef()
	{
		final Pricing p = new Pricing(new XMLDoc("Pricing", null).getRoot());
		p.addPrice("p1", 20).setLineID("L1");
		p.addPrice("p2", 22).setLineID("L2");
		final Price p3 = p.addPrice("p3", 42);
		p3.addLineIDRef("L1");
		p3.addLineIDRef("L2");
		assertTrue(p3.getLineIDRefs().contains("L1"));
	}
}
