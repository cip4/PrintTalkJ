package org.cip4.printtalk;

import java.util.zip.DataFormatException;

import org.cip4.jdflib.JDFTestCaseBase;
import org.cip4.jdflib.util.JDFDate;
import org.cip4.printtalk.PrintTalk.EnumBusinessObject;

public class CreditCardTest extends JDFTestCaseBase
{

	/**
	 * @throws DataFormatException 
	 * 
	 */
	public void testSetExpires() throws DataFormatException
	{
		Invoice invoice = (Invoice) new PrintTalk().appendRequest(EnumBusinessObject.Invoice, null);
		JDFDate expires = new JDFDate("2011-09");
		CreditCard cc = invoice.getCreatePricing().getCreatePayment().getCreateCreditCard();
		cc.setExpires(expires);

		System.out.println("cc.getExpires: " + cc.getExpires());
		assertEquals(expires.getMonth(), cc.getExpires().getMonth());
		assertEquals(9, cc.getExpires().getMonth());
		assertEquals(expires.getYear(), cc.getExpires().getYear());
		assertEquals(2011, cc.getExpires().getYear());
	}

}
