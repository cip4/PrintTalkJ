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

import java.util.ArrayList;
import java.util.List;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.core.VString;
import org.cip4.jdflib.extensions.XJDFConstants;
import org.cip4.jdflib.util.ContainerUtil;
import org.cip4.jdflib.util.StringUtil;

/**
 * Class represented OrderStatusRequest business object.
 *
 * @author rainer prosi
 *
 */
public class StockLevelRequest extends BusinessObject
{
	/** **/
	public static enum EnumAvailability
	{
		Available, Deliverable, Undeliverable
	}

	/** */
	public final static String ATTR_AVAILABILITY = "Availability";
	/** */
	public final static String ATTR_DISPLAYPRICE = "DisplayPrice";

	/**
	 *
	 * @param theElement
	 */
	public StockLevelRequest(final KElement theElement)
	{
		super(theElement);
	}

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
		setAttribute(ATTR_CURRENCY, currency);
	}

	/**
	 * get displayPrice value
	 *
	 * @return
	 */
	public boolean getDisplayPrice()
	{
		return StringUtil.parseBoolean(getAttribute(ATTR_DISPLAYPRICE), true);
	}

	/**
	 * set displayPrice value
	 *
	 * @param displayPrice
	 */
	public void setDisplayPrice(final boolean displayPrice)
	{
		setAttribute(ATTR_DISPLAYPRICE, Boolean.toString(displayPrice));
	}

	/**
	 * get displayPrice value
	 *
	 * @return
	 */
	public List<EnumAvailability> getAvailability()
	{
		final VString v = StringUtil.tokenize(getAttribute(ATTR_AVAILABILITY), null, false);
		final ArrayList<EnumAvailability> ret = new ArrayList<>();
		for (final String s : v)
		{
			final EnumAvailability valueOf = EnumAvailability.valueOf(s);
			if (valueOf != null)
				ret.add(valueOf);
		}
		return ret;

	}

	/**
	 * set currency value
	 *
	 * @param availability
	 */
	public void setAvailability(final List<EnumAvailability> availability)
	{
		if (ContainerUtil.isEmpty(availability))
		{
			setAttribute(ATTR_AVAILABILITY, null);
		}
		else
		{
			final VString newString = new VString();
			for (final EnumAvailability a : availability)
			{
				newString.add(a.name());
			}
			setVAttribute(ATTR_AVAILABILITY, newString);
		}
	}

	/**
	 * set availability value
	 *
	 * @param availability
	 */
	public void addAvailability(final EnumAvailability availability)
	{
		List<EnumAvailability> l = getAvailability();
		if (l == null)
			l = new ArrayList<>();
		if (availability != null && !l.contains(availability))
			l.add(availability);
		setAvailability(l);
	}

}
