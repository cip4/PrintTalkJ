/**
 * The CIP4 Software License, Version 1.0
 *
 * Copyright (c) 2001-2022 The International Cooperation for the Integration of Processes in Prepress, Press and Postpress (CIP4). All rights reserved.
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
package org.cip4.printtalk.builder;

import org.cip4.jdflib.extensions.XJDFHelper;
import org.cip4.printtalk.BusinessObject;
import org.cip4.printtalk.Credential;
import org.cip4.printtalk.HeaderBase;
import org.cip4.printtalk.HeaderBase.EnumHeaderType;
import org.cip4.printtalk.PrintTalk;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;
import org.cip4.printtalk.PurchaseOrder;

public class PrintTalkBuilder
{
	int version;
	String customerID;
	String userAgent;

	public String getUserAgent()
	{
		return userAgent;
	}

	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	String userID;
	String shopID;
	String to;
	String toURL;
	String fromURL;
	EnumBusinessObject businessObject;
	XJDFHelper xjdf;
	boolean wantSender;

	/**
	 * @return the version
	 */
	public int getVersion()
	{
		return version;
	}

	/**
	 * @param version the version to set 20=2.0 etc
	 */
	public void setVersion(final int major, final int minor)
	{
		this.version = 10 * major + minor;
	}

	/**
	 *
	 * @param factory
	 */
	PrintTalkBuilder(final PrintTalkBuilder factory)
	{
		super();
		version = factory.version;
		to = factory.to;
		fromURL = factory.fromURL;
		toURL = factory.toURL;
		shopID = factory.shopID;
		userID = factory.userID;
		customerID = factory.customerID;
		businessObject = factory.businessObject;
		xjdf = factory.xjdf;
		wantSender = factory.wantSender;
		userAgent = factory.getUserAgent();
	}

	/**
	 *
	 */
	PrintTalkBuilder()
	{
		version = PrintTalk.getDefaultVersion();
		reset();
	}

	/**
	 *
	 */
	public void reset()
	{
		customerID = null;
		shopID = null;
		to = null;
		userID = null;
		fromURL = null;
		toURL = null;
		wantSender = false;
		userAgent = null;
		resetInstance();
	}

	/**
	 *
	 */
	public void resetInstance()
	{
		businessObject = null;
		xjdf = null;
	}

	/**
	 *
	 * @return
	 */
	public PrintTalk getPrintTalk()
	{
		final PrintTalk pt = new PrintTalk(version);
		setCredentials(pt);
		setRequest(pt);
		return pt;
	}

	void setRequest(final PrintTalk pt)
	{
		if (businessObject != null)
		{
			final BusinessObject bo = pt.appendRequest(businessObject, null);
			if (xjdf != null && bo instanceof PurchaseOrder)
			{
				final PurchaseOrder po = (PurchaseOrder) bo;
				po.setXJDF(xjdf);
			}
		}

	}

	/**
	 *
	 * @param pt
	 */
	void setCredentials(final PrintTalk pt)
	{
		if (customerID != null)
		{
			final Credential cred = pt.setCredential(EnumHeaderType.From, Credential.DOMAIN_CUSTOMERID, customerID);
		}
		if (fromURL != null)
		{
			pt.setCredential(EnumHeaderType.From, Credential.DOMAIN_URL, fromURL);
		}
		if (shopID != null)
		{
			final Credential cred = pt.setCredential(wantSender ? EnumHeaderType.Sender : EnumHeaderType.From, Credential.DOMAIN_SHOP_ID, shopID);
		}
		if (userID != null)
		{
			final Credential cred = pt.setCredential(wantSender ? EnumHeaderType.Sender : EnumHeaderType.From, Credential.DOMAIN_USER_ID, userID);
		}
		if (to != null)
		{
			pt.setCredential(EnumHeaderType.To, Credential.DOMAIN_SENDER_ID, to);
		}
		if (toURL != null)
		{
			pt.setCredential(EnumHeaderType.To, Credential.DOMAIN_URL, toURL);
		}
		if (userAgent != null)
		{
			for (EnumHeaderType ht : EnumHeaderType.values())
			{
				HeaderBase h = pt.getHeader(ht);
				if (h != null)
				{
					h.setUserAgent(userAgent);
				}
			}
		}

	}

	/**
	 * @return the userID
	 */
	public String getUserID()
	{
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(final String userID)
	{
		this.userID = userID;
	}

	/**
	 * @return the to
	 */
	public String getTo()
	{
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(final String to)
	{
		this.to = to;
	}

	/**
	 * @param to the fromURL to set
	 */
	public void setFromURL(final String fromURL)
	{
		this.fromURL = fromURL;
	}

	/**
	 * @param to the toURL to set
	 */
	public void setToURL(final String toURL)
	{
		this.toURL = toURL;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(final int version)
	{
		this.version = version;
	}

	/**
	 * @return the shopID
	 */
	public String getShopID()
	{
		return shopID;
	}

	/**
	 * @param shopID the shopID to set
	 */
	public void setShopID(final String shopID)
	{
		this.shopID = shopID;
	}

	/**
	 * @return the customerID
	 */
	public String getCustomerID()
	{
		return customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(final String customerID)
	{
		this.customerID = customerID;
	}

	/**
	 * @return the businessObject
	 */
	public EnumBusinessObject getBusinessObject()
	{
		return businessObject;
	}

	/**
	 * @param businessObject the businessObject to set
	 */
	public void setBusinessObject(final EnumBusinessObject businessObject)
	{
		this.businessObject = businessObject;
	}

	/**
	 * @return the xjdf
	 */
	public XJDFHelper getXjdf()
	{
		return xjdf;
	}

	/**
	 * @param xjdf the xjdf to set
	 */
	public void setXjdf(final XJDFHelper xjdf)
	{
		this.xjdf = xjdf;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PrintTalkBuilder [version=" + version + ", customerID=" + customerID + ", userID=" + userID + ", shopID=" + shopID + ", to=" + to + ", businessObject="
				+ businessObject + "]";
	}

	/**
	 * @return the wantSender
	 */
	public boolean isWantSender()
	{
		return wantSender;
	}

	/**
	 * @param wantSender the wantSender to set
	 */
	public void setWantSender(final boolean wantSender)
	{
		this.wantSender = wantSender;
	}

}
