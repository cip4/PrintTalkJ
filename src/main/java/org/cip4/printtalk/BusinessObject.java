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

import org.cip4.jdflib.core.AttributeName;
import org.cip4.jdflib.core.JDFElement;
import org.cip4.jdflib.core.KElement;
import org.cip4.jdflib.extensions.XJDFConstants;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;

/**
 *
 * @author rainer prosi
 * @date Jan 3, 2011
 */
public abstract class BusinessObject extends AbstractPrintTalk
{
	public static final String ATTR_BUSINESSREFID = "BusinessRefID";
	public static final String ATTR_BUSINESSID = "BusinessID";
	public static final String ATTR_CURRENCY = PrintTalkConstants.Currency;
	public static final String ATTR_EXPIRES = "Expires";

	public enum EnumResult
	{
		Accepted, AcceptedWaiting, Rejected
	}

	public enum EnumUpdateMethod
	{
		Add, Replace
	}

	/**
	 * note that it is not necessarily always legal to add a JDF to an arbitrary bo
	 *
	 * @param rootName
	 * @param iSkip
	 * @return
	 * @deprecated use the xjdf methods of the appropriate business objects
	 */
	@Deprecated
	public KElement getCreateJDFRoot(final String rootName, final int iSkip)
	{
		final String schemaURL = (XJDFConstants.XJDF.equals(rootName)) ? JDFElement.getSchemaURL(2, 0) : JDFElement.getSchemaURL();

		final KElement element = theElement.getCreateElement(rootName, schemaURL, iSkip);
		element.init();
		return element;
	}

	/**
	 *
	 * @param theElement
	 */
	public BusinessObject(final KElement theElement)
	{
		super(theElement);
	}

	MasterContract getCreateMasterContract(final String contractID)
	{
		final MasterContract masterContract = new MasterContract(getCreateElement(PrintTalkConstants.MasterContract, 0));
		masterContract.setContractID(contractID);
		return masterContract;
	}

	/**
	 * get the existing MasterContract element
	 *
	 *
	 * @return
	 */
	MasterContract getMasterContract()
	{
		final KElement mc = getElement(PrintTalkConstants.MasterContract, 0);
		return mc == null ? null : new MasterContract(mc);
	}

	/**
	 * get the existing MasterContract/@ContractID
	 *
	 *
	 * @return
	 */
	String getMasterContractID()
	{
		final MasterContract masterContract = getMasterContract();
		return masterContract == null ? null : masterContract.getContractID();
	}

	/**
	 *
	 * @see org.cip4.printtalk.AbstractPrintTalk#init()
	 */
	@Override
	public void init()
	{
		super.init();
		setBusinessID(theElement.getLocalName() + KElement.uniqueID(0));
	}

	/**
	 *
	 * @return id
	 */
	public String getBusinessID()
	{
		final String bid = getAttribute(ATTR_BUSINESSID);
		if (bid != null)
			return bid;
		final KElement request = getRequest();
		return request == null ? null : request.getAttribute(ATTR_BUSINESSID);
	}

	/**
	 *
	 * @param id
	 */
	public void setBusinessID(final String id)
	{
		final KElement request = getRequest();
		if (request != null)
		{
			request.setAttribute(ATTR_BUSINESSID, id);
		}
	}

	/**
	 *
	 * @return
	 */
	public KElement getRequest()
	{
		return getRoot() == null ? null : getRoot().getParentNode_KElement();
	}

	/**
	 *
	 * @return id
	 */
	public String getBusinessRefID()
	{
		final String bid = getAttribute(ATTR_BUSINESSREFID);
		if (bid != null)
			return bid;
		final KElement request = getRequest();
		return request == null ? null : request.getAttribute(ATTR_BUSINESSREFID);
	}

	/**
	 *
	 * @return
	 */
	public String getCustomerProjectID()
	{
		final KElement request = getRequest();
		return request == null ? null : request.getAttribute(AttributeName.CUSTOMERPROJECTID);
	}

	/**
	 *
	 * @param coid
	 */
	public void setCustomerProjectID(final String coid)
	{

		final KElement request = getRequest();
		if (request != null)
		{
			request.setAttribute(AttributeName.CUSTOMERPROJECTID, coid);
		}
	}

	/**
	 *
	 * @return id
	 */
	@Override
	public String getDescriptiveName()
	{
		final String desc = super.getDescriptiveName();
		if (desc != null)
			return desc;
		final KElement request = getRequest();
		return request == null ? null : request.getNonEmpty(AttributeName.DESCRIPTIVENAME);
	}

	/**
	 *
	 * @param id
	 */
	public void setBusinessRefID(final String id)
	{

		final KElement request = getRequest();
		if (request != null)
		{
			request.setAttribute(ATTR_BUSINESSREFID, id);
		}
	}

	/**
	 *
	 * @param id
	 */
	@Override
	public void setDescriptiveName(final String desc)
	{

		final KElement request = getRequest();
		if (request != null)
		{
			request.setNonEmpty(AttributeName.DESCRIPTIVENAME, desc);
		}
	}

	/**
	 *
	 * @param ref the bo to reference
	 */
	public void setRef(final PrintTalk ref)
	{
		final BusinessObject boRef = ref == null ? null : ref.getBusinessObject();
		if (ref != null)
			setBusinessRefID(boRef.getBusinessID());

	}

	/**
	 * factory method for printtalk business objects
	 *
	 * @param e
	 * @return
	 * @throws IllegalArgumentException if the element is not a business object
	 */
	static BusinessObject getBusinessObject(final KElement e)
	{
		if (e == null)
			return null;

		final String boName = e.getLocalName();
		final BusinessObject businessObject;
		if (EnumBusinessObject.ContentDelivery.name().equals(boName))
		{
			businessObject = new ContentDelivery(e);
		}
		else if (EnumBusinessObject.ContentDeliveryResponse.name().equals(boName))
		{
			businessObject = new ContentDeliveryResponse(e);
		}
		else if (EnumBusinessObject.RFQ.name().equals(boName))
		{
			businessObject = new RFQ(e);
		}
		else if (EnumBusinessObject.Quotation.name().equals(boName))
		{
			businessObject = new Quotation(e);
		}
		else if (EnumBusinessObject.PurchaseOrder.name().equals(boName))
		{
			businessObject = new PurchaseOrder(e);
		}
		else if (EnumBusinessObject.Confirmation.name().equals(boName))
		{
			businessObject = new Confirmation(e);
		}
		else if (EnumBusinessObject.Cancellation.name().equals(boName))
		{
			businessObject = new Cancellation(e);
		}
		else if (EnumBusinessObject.Refusal.name().equals(boName))
		{
			businessObject = new Refusal(e);
		}
		else if (EnumBusinessObject.OrderStatusRequest.name().equals(boName))
		{
			businessObject = new OrderStatusRequest(e);
		}
		else if (EnumBusinessObject.OrderStatusResponse.name().equals(boName))
		{
			businessObject = new OrderStatusResponse(e);
		}
		else if (EnumBusinessObject.ProofApprovalRequest.name().equals(boName))
		{
			businessObject = new ProofApprovalRequest(e);
		}
		else if (EnumBusinessObject.ProofApprovalResponse.name().equals(boName))
		{
			businessObject = new ProofApprovalResponse(e);
		}
		else if (EnumBusinessObject.Invoice.name().equals(boName))
		{
			businessObject = new Invoice(e);
		}
		else if (EnumBusinessObject.StockLevelRequest.name().equals(boName))
		{
			businessObject = new StockLevelRequest(e);
		}
		else if (EnumBusinessObject.StockLevelResponse.name().equals(boName))
		{
			businessObject = new StockLevelResponse(e);
		}
		else if (EnumBusinessObject.ReturnJob.name().equals(boName))
		{
			businessObject = new ReturnJob(e);
		}
		else if (EnumBusinessObject.ArtDeliveryRequest.name().equals(boName))
		{
			businessObject = new ArtDeliveryRequest(e);
		}
		else if (EnumBusinessObject.ArtDeliveryResponse.name().equals(boName))
		{
			businessObject = new ArtDeliveryResponse(e);
		}
		else
		{
			throw new IllegalArgumentException("BusinessObject type not yet implemented: " + boName);
		}
		return businessObject;
	}

	/**
	 * set the expires dates
	 *
	 * @param i
	 */
	JDFDate getExpirationDays(final int days)
	{
		final JDFDate d = new JDFDate();
		d.setTime(JDFDate.getDefaultHour(), 0, 0);
		d.addOffset(0, 0, 0, days);
		return d;
	}

}
