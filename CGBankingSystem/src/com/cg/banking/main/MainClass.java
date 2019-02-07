package com.cg.banking.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;
import com.cg.banking.exceptions.AccountBlockedException;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServicesDownException;
import com.cg.banking.exceptions.InsufficientAmountException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.exceptions.InvalidPinNumberException;
import com.cg.banking.services.BankingServices;
import com.cg.banking.services.BankingServicesImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class MainClass {
	public static void main(String[] args) throws AccountNotFoundException, AccountBlockedException, InsufficientAmountException, InvalidPinNumberException {
		BankingServices bankingServices = new BankingServicesImpl();
		Scanner scanner = new Scanner(System.in);
		int choice=0,pinNumber;
		float amount;
		long accountNo,toAccountNo,fromAccountNo;
		Account account;
		String accountType;
		try {
			do { 
				System.out.println(">>>>-------------------------------<<<<\n\n1. Open Account \n2. Deposit Amount \n3. Withdraw Amount \n4. Fund Transfer \n5. Get Account Details \n6. Get All Accounts Detail \n7. Get Account All transactions Details \n8. Get Account Status \n9. Exit \n>>>>-------------------------------<<<<");
				choice = scanner.nextInt();
				scanner.nextLine();
				switch(choice) {
				case 1:	//OPEN NEW ACCOUNT
					System.out.print("Enter account Type : ");
					accountType = scanner.nextLine();
					System.out.print("Enter Initial balance : ");
					amount = scanner.nextInt();
					account = bankingServices.openAccount(accountType, amount);
					System.out.println("Account Opened Successfully \nAccount Number : "+account.getAccountNo()+" \nPin number : "+account.getPinNumber()+" \nAvailable balance : "+account.getAccountBalance());
					break;
				case 2:	//DEPOSIT AMOUNT
					System.out.print("Enter account number : ");
					accountNo = scanner.nextLong();
					System.out.println("Enter amount to deposit : ");
					amount = scanner.nextFloat();
					System.out.println("Your Current Account Balance is: "+bankingServices.depositAmount(accountNo, amount));
					break;
				case 3:	//WITHDRAW AMOUNT
					System.out.print("Enter account number : ");
					accountNo = scanner.nextLong();
					System.out.println("Enter amount to withdraw : ");
					amount = scanner.nextFloat();
					System.out.println("Enter pinNumber : ");
					pinNumber = scanner.nextInt();
					System.out.println("Your Current Account Balance is: "+bankingServices.withdrawAmount(accountNo, amount, pinNumber));
					break;
				case 4:	//FUND TRANSFER
					System.out.print("Enter your account number : ");
					fromAccountNo = scanner.nextLong();
					System.out.print("Enter receiver account number : ");
					toAccountNo= scanner.nextLong();
					System.out.print("Enter your transfer amount : ");
					amount = scanner.nextFloat();
					System.out.println("Enter your pinNumber : ");
					pinNumber = scanner.nextInt();
					if(bankingServices.fundTransfer(toAccountNo, fromAccountNo, amount, pinNumber))
						System.out.println("Funds transferred successfully.");
					break;
				case 5:	//GET ACCOUNT DETAILS
					System.out.print("Enter account number : ");
					accountNo = scanner.nextLong();
					System.out.println(bankingServices.getAccountDetails(accountNo).toString());
					break;
				case 6:	//GET ALL ACCOUNTS DETAIL
					System.out.println("All Accounts Details");
					System.out.println(bankingServices.getAllAccountDetails());
					break;
				case 7:	//GET ACCOUNT ALL TRANSACTIONS
					System.out.print("Enter account number : ");
					accountNo = scanner.nextLong();
					bankingServices.pdfGenerator(accountNo);
					System.out.println("transactions of Account No, "+accountNo+" -->");
					for(Transaction t: new ArrayList<>(bankingServices.getAccountAllTransaction(accountNo))) {
						System.out.println(t);
					}
					break;
				case 8:	//GET ACCOUNT STATUS
					System.out.println("Enter your account Number : ");
					accountNo = scanner.nextLong();
					System.out.println("Your status is : "+bankingServices.accountStatus(accountNo));
					break;
				case 9:	//EXIT
					System.out.println("Thank You For Banking With Us.");
					System.exit(0);
					break;
				default:
					System.out.println("Please Enter Correct Choice!!!");
					
				}
			}while(choice!=9);
		} catch (InvalidAmountException | InvalidAccountTypeException |  BankingServicesDownException | AccountNotFoundException | AccountBlockedException | InsufficientAmountException | InvalidPinNumberException e )  {
			e.printStackTrace();
			System.exit(0);;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
