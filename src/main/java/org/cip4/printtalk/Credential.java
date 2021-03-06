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

import org.cip4.jdflib.core.KElement;

/**
 *
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public class Credential extends AbstractPrintTalk
{
	public static final String DOMAIN = "domain";
	public static final String IDENTITY = "Identity";
	@Deprecated
	public static final String SHARED_SECRET = "SharedSecret";
	/** */
	public final static String ELEMENT_CREDENTIAL = "Credential";
	/**
	 * @deprecated use senderid in 2.0
	 */
	@Deprecated
	public final static String DOMAIN_AGENTID = "jdf:AgentID";
	public final static String DOMAIN_CUSTOMERID = "CustomerID";
	public final static String DOMAIN_COMPANYID = "CompanyID";
	public static final String DOMAIN_SHOP_ID = "ShopID";
	public static final String DOMAIN_SENDER_ID = "SenderID";
	public static final String DOMAIN_USER_ID = "UserID";
	public static final String DOMAIN_RESPONSE_URL = "ResponseURL";
	public static final String DOMAIN_URL = "URL";
	public static final String DOMAIN_EMAIL = "Email";
	public static final String DOMAIN_DUNS = "DUNS";

	/**
	 * @param header
	 */
	public Credential(final KElement header)
	{
		super(header);
	}

	/**
	 *
	 * @return
	 */
	public String getIdentity()
	{
		return getXPathAttribute(IDENTITY, null);
	}

	/**
	 *
	 * @param value
	 */
	@Deprecated
	public void setSharedSecret(final String value)
	{
		setXPathValue(SHARED_SECRET, value);
	}

	/**
	 *
	 * @return
	 */
	@Deprecated
	public String getSharedSecret()
	{
		return getXPathAttribute(SHARED_SECRET, null);
	}

	/**
	 *
	 * @param value
	 */
	public void setIdentity(final String value)
	{
		setXPathValue(IDENTITY, value);
	}

	/**
	 * set the domain to domain
	 *
	 * @param domain
	 */
	public void setDomain(final String domain)
	{
		setAttribute(DOMAIN, domain);
	}

	/**
	 * get the domain
	 *
	 * @return domain
	 */
	public String getDomain()
	{
		return getAttribute(DOMAIN);
	}
}
