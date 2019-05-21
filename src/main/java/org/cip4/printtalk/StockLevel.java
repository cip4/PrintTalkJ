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

import java.util.List;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.extensions.XJDFConstants;
import org.cip4.jdflib.ifaces.IMatches;
import org.cip4.jdflib.resource.JDFLocation;
import org.cip4.jdflib.util.JDFDuration;
import org.cip4.jdflib.util.StringUtil;
import org.cip4.printtalk.StockLevelRequest.EnumAvailability;

/**
 * Class represented StockLevel element.
 *
 */
public class StockLevel extends AbstractPrintTalk implements IMatches
{
	/** **/
	public final static String ELEMENT_STOCKLEVEL = "StockLevel";
	/** **/
	public final static String ATTR_PRODUCTIONDURATION = "ProductionDuration";
	/** **/
	public final static String ATTR_LOT = "Lot";

	/**
	 *
	 * @param theElement
	 */
	public StockLevel(final KElement theElement)
	{
		super(theElement);
	}

	/**
	 * @see org.cip4.jdflib.ifaces.IMatches#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(final Object stockLevelRequest)
	{
		if (stockLevelRequest == null)
			return true;
		if (stockLevelRequest instanceof String)
		{
			return StringUtil.matchesSimple(getExternalID(), (String) stockLevelRequest);
		}
		else if (stockLevelRequest instanceof EnumAvailability)
		{
			final EnumAvailability avail = (EnumAvailability) stockLevelRequest;
			return avail.equals(getAvailability());
		}

		if (!(stockLevelRequest instanceof StockLevelRequest))
			return false;

		final StockLevelRequest stockLevelReq = (StockLevelRequest) stockLevelRequest;
		final String productFilter = stockLevelReq.getExternalID();
		if (!matches(productFilter))
			return false;
		final List<EnumAvailability> avail = stockLevelReq.getAvailability();
		if (avail != null)
		{
			boolean match = false;
			for (final EnumAvailability a : avail)
			{
				if (a.equals(getAvailability()))
				{
					match = true;
					break;
				}
			}
			if (!match)
				return false;
		}
		// TODO more filters
		return true;
	}

	/**
	 *
	 * @param productID
	 * @deprecated use setExteralID
	 */
	@Deprecated
	public void setProductID(final String productID)
	{
		setExternalID(productID);
	}

	/**
	 *
	 * @param productID
	 */
	public void setExternalID(final String productID)
	{
		setAttribute(XJDFConstants.ExternalID, productID);
	}

	/**
	 *
	 * @return
	 */
	public String getExternalID()
	{
		return getAttribute(XJDFConstants.ExternalID);
	}

	/**
	 *
	 * @return
	 * @deprecated use getExternalID
	 */
	@Deprecated
	public String getProductID()
	{
		String pid = getAttribute(AttributeName.PRODUCTID);
		if (pid == null)
			pid = getExternalID();
		return pid;
	}

	/**
	 *
	 * @return
	 */
	public String getLot()
	{
		return getAttribute(ATTR_LOT);
	}

	/**
	 *
	 * @param lot
	 */
	public void setLot(final String lot)
	{
		setAttribute(ATTR_LOT, lot);
	}

	/**
	 *
	 * @return
	 */
	public int getAmount()
	{
		return StringUtil.parseInt(getAttribute(AttributeName.AMOUNT), -1);
	}

	/**
	 *
	 * @param amount
	 */
	public void setAmount(final int amount)
	{
		setAttribute(AttributeName.AMOUNT, "" + amount);
	}

	/**
	 *
	 * @return
	 */
	public JDFDuration getProductionDuration()
	{
		return JDFDuration.createDuration(getAttribute(ATTR_PRODUCTIONDURATION));
	}

	/**
	 *
	 * @param dur
	 */
	public void setProductionDuration(final JDFDuration dur)
	{
		setAttribute(ATTR_PRODUCTIONDURATION, dur == null ? null : dur.getDurationISO());
	}

	/**
	 *
	 *
	 * @return
	 */
	public Price getPrice()
	{
		final KElement element = getElement(Price.ELEMENT_PRICE);
		return element == null ? null : new Price(element);
	}

	/**
	 *
	 *
	 * @return
	 */
	public JDFLocation getLocation()
	{
		return (JDFLocation) getElement(ElementName.LOCATION);
	}

	/**
	 *
	 *
	 * @return
	 */
	public JDFLocation appendLocation()
	{
		return (JDFLocation) appendElement(ElementName.LOCATION);
	}

	/**
	 *
	 *
	 * @return
	 */
	public Price getCreatePrice()
	{
		final KElement element = getCreateElement(Price.ELEMENT_PRICE);
		return element == null ? null : new Price(element);
	}

	/**
	 * get availability value
	 *
	 * @return
	 */
	public EnumAvailability getAvailability()
	{
		return EnumAvailability.valueOf(getAttribute(StockLevelRequest.ATTR_AVAILABILITY, EnumAvailability.Undeliverable.name()));
	}

	/**
	 * set availability value
	 *
	 * @param availability
	 */
	public void setAvailability(final EnumAvailability availability)
	{
		setAttribute(StockLevelRequest.ATTR_AVAILABILITY, availability == null ? null : availability.name());
	}

}
