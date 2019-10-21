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
import java.util.zip.DataFormatException;

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.ElementName;
import org.cip4.jdflib.core.JDFComment;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.datatypes.JDFAttributeMap;
import org.cip4.jdflib.resource.process.JDFContact;
import org.cip4.jdflib.util.JDFDate;

/**
 * Class represented Payment element.
 *
 * @since PrintTalk 1.3
 */
public class Payment extends AbstractPrintTalk
{
	/** */
	public final static String ELEMENT_PAYMENT = "Payment";

	@Deprecated
	public final static String ELEMENT_PAYTERM = "PayTerm";

	/** */
	public final static String ATTR_PAYMENT_TYPE = "PaymentType";
	public final static String ATTR_PAYMENT_TYPE_DETAILS = "PaymentTypeDetails";

	/** */
	public final static String ELEMENT_CREDITCARD = "CreditCard";
	/** */
	public final static String ATTR_AUTHORIZATION = "Authorization";
	/** */
	public final static String ATTR_AUTHORIZATIONEXPIRES = "AuthorizationExpires";
	/** */
	@Deprecated
	public final static String ATTR_NUMBER = "Number";
	/** */
	public final static String ATTR_TYPE = "Type";
	/** */
	public final static String FORMAT_YEARMONTH = "yyyy-MM";

	public enum EnumPaymentType
	{
		BankTransfer, Contract, CreditCard, DebitCard, DigitalCurrency, Invoice, PaymentProvider, Other
	}

	/**
	 *
	 * @param theElement
	 */
	public Payment(final KElement theElement)
	{
		super(theElement);
	}

	/**
	 * get pay term value
	 *
	 * @return
	 */
	public String getPayTerm()
	{
		return getTElem(ElementName.COMMENT);
	}

	/**
	 * set pay term value
	 *
	 * @param s
	 */
	public void setPayTerm(final String s)
	{
		final JDFComment c = (JDFComment) getCreateXJDFElement(ElementName.COMMENT, 0);
		c.setText(s);
		c.setAttribute(AttributeName.TYPE, "ptk:PaymentTerms");
	}

	/**
	 * create credit card element
	 *
	 * @return
	 */
	@Deprecated
	public CreditCard getCreateCreditCard()
	{
		return new CreditCard(getCreateElement(CreditCard.ELEMENT_CREDITCARD));
	}

	/**
	 *
	 * @return
	 */
	public JDFContact getCreateContact()
	{
		return (JDFContact) getCreateXJDFElement(ElementName.CONTACT, 0);
	}

	/**
	 *
	 * @return
	 */
	public JDFContact getContact()
	{
		return (JDFContact) getElement(ElementName.CONTACT);
	}

	/**
	 * get credit card element
	 *
	 * @return
	 */
	@Deprecated
	public CreditCard getCreditCard()
	{
		return new CreditCard(getElement(CreditCard.ELEMENT_CREDITCARD));
	}

	/**
	 * get authorization value
	 *
	 * @return
	 */
	public String getAuthorization()
	{
		return getAttribute(ATTR_AUTHORIZATION);
	}

	/**
	 * set authorization value
	 *
	 * @param s
	 */
	public void setAuthorization(final String s)
	{
		setAttribute(ATTR_AUTHORIZATION, s);
	}

	/**
	 * get authorization expires value
	 *
	 * @return
	 */
	public JDFDate getAuthorizationExpires()
	{
		final String s = getAttribute(ATTR_AUTHORIZATIONEXPIRES);

		try
		{
			return (s == null) ? null : new JDFDate(s);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
	}

	/**
	 * set authorization expires value
	 *
	 * @param expires
	 */
	public void setAuthorizationExpires(final JDFDate expires)
	{
		setAttribute(ATTR_AUTHORIZATIONEXPIRES, expires == null ? null : expires.getFormattedDateTime(FORMAT_YEARMONTH));
	}

	/**
	 * get expires value
	 *
	 * @return
	 */
	public JDFDate getExpires()
	{
		final String s = getAttribute(BusinessObject.ATTR_EXPIRES);

		try
		{
			return (s == null) ? null : new JDFDate(s);
		}
		catch (final DataFormatException e)
		{
			return null;
		}
	}

	/**
	 * set expires value
	 *
	 * @param expires
	 */
	public void setExpires(final JDFDate expires)
	{
		setAttribute(BusinessObject.ATTR_EXPIRES, expires == null ? null : expires.getFormattedDateTime(FORMAT_YEARMONTH));
	}

	/**
	 * get number value
	 *
	 * @return
	 */
	public String getNumber()
	{
		final String number = getGeneralID("CardNumber");
		return number == null ? getAttribute(ATTR_NUMBER) : number;
	}

	/**
	 * set number value
	 *
	 * @param s
	 */
	public void setNumber(final String number)
	{
		setGeneralID("CardNumber", number);
	}

	public String getGeneralID(final String idUsage)
	{
		final List<KElement> v = this.theElement.getChildArray_KElement(ElementName.GENERALID, null, new JDFAttributeMap(AttributeName.IDUSAGE, idUsage), true, 0);
		final KElement jdfGeneralID = v.isEmpty() ? null : v.get(0);
		return jdfGeneralID == null ? null : jdfGeneralID.getNonEmpty(AttributeName.IDVALUE);
	}

	public void setGeneralID(final String idUsage, final String idValue)
	{
		final List<KElement> v = this.theElement.getChildArray_KElement(ElementName.GENERALID, null, new JDFAttributeMap(AttributeName.IDUSAGE, idUsage), true, 0);
		if (!v.isEmpty())
		{
			final KElement gid = v.get(0);

			// remove any duplicates
			for (int i = 1; i < v.size(); i++)
			{
				v.get(i).deleteNode();
			}
			if (idValue == null)
			{
				gid.deleteNode();
			}
			else
			{
				gid.setAttribute(AttributeName.IDVALUE, idValue);
			}
		}
		else
		{
			appendGeneralID(idUsage, idValue);
		}
	}

	public void appendGeneralID(final String idUsage, final String idValue)
	{
		final KElement gid = appendXJDFElement(ElementName.GENERALID);
		gid.setAttribute(AttributeName.IDUSAGE, idUsage);
		gid.setAttribute(AttributeName.IDVALUE, idValue);
	}

	/**
	 *
	 *
	 * @param priceType
	 *
	 */
	public void setPaymentType(final EnumPaymentType payType)
	{
		theElement.setAttribute(ATTR_PAYMENT_TYPE, payType == null ? null : payType.name(), null);
	}

	/**
	 *
	 *
	 * @return
	 */
	public EnumPaymentType getPaymentType()
	{
		final String s = getAttribute(ATTR_PAYMENT_TYPE);
		try
		{
			return s == null ? null : EnumPaymentType.valueOf(s);
		}
		catch (final Exception x)
		{
			return null;
		}
	}

}
