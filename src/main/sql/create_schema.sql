-- 1) Create the database and switch to it
DROP DATABASE IF EXISTS `Bank`;
CREATE database `Bank`;
USE `Bank`;

-- 2) Table definitions

-- Customers table
CREATE TABLE Customers (
  customerID   INT            NOT NULL AUTO_INCREMENT,
  firstName    VARCHAR(30)    NOT NULL,
  lastName     VARCHAR(30)    NOT NULL,
  address      VARCHAR(40)    NOT NULL,
  phone        CHAR(10)       NOT NULL CHECK (phone REGEXP '^[0-9]{10}$'),
  email        VARCHAR(30)    NOT NULL CHECK (email LIKE '%_@__%.__%'),
  password     VARCHAR(30)    NOT NULL,
  PRIMARY KEY (CustomerID)
);

-- Account table
CREATE TABLE Accounts (
  accountID    INT            NOT NULL AUTO_INCREMENT,
  customerID   INT            NOT NULL,
  accountType  CHAR(20)       NOT NULL CHECK (accountType IN ('SAVINGS', 'CHECKING')),
  balance      DOUBLE         NOT NULL CHECK (balance >= 0),
  interest     DECIMAL(4, 3)  NOT NULL CHECK (interest > 0),
  PRIMARY KEY (AccountID),
  FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Transactions table
CREATE TABLE Transactions (
  transactionID   INT         NOT NULL AUTO_INCREMENT,
  transactionType CHAR(20)    NOT NULL CHECK (transactionType IN ('DEPOSIT', 'WITHDRAWAL', 'PAYMENT', 'SENT', 'RECEIVED')),
  accountID       INT         NOT NULL,
  timestamp       TIMESTAMP   NOT NULL,
  amount          DOUBLE      NOT NULL CHECK (amount > 0),
  PRIMARY KEY (transactionID),
  FOREIGN KEY (accountID) REFERENCES Accounts(accountID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Loans table
CREATE TABLE Loans (
  loanID          INT           NOT NULL AUTO_INCREMENT,
  loanStatus      VARCHAR(10)   NOT NULL CHECK (loanStatus IN ('PENDING', 'APPROVED', 'PAID')),
  loanType	      VARCHAR(10)	NOT NULL CHECK (loanType IN ('HOME', 'STUDENT', 'PERSONAL', 'AUTO')),
  loanAmount      DOUBLE        NOT NULL CHECK (loanAmount > 0),
  balance		  DOUBLE        NOT NULL CHECK (balance >= 0),
  customerID      INT           NOT NULL,
  timestamp       TIMESTAMP     NOT NULL,
  interestRate    DECIMAL(4, 3) NOT NULL CHECK (interestRate > 0),
  term			  INT			NOT NULL CHECK (term > 0),
  PRIMARY KEY (loanID),
  FOREIGN KEY (customerID) REFERENCES Customers(customerID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE LoanPayments (
  transactionID  INT NOT NULL,
  loanID         INT NOT NULL,
  PRIMARY KEY (transactionID, loanID),
  FOREIGN KEY (TransactionID) REFERENCES Transactions(TransactionID)
	ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (LoanID) REFERENCES Loans(loanID)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE INDEX accountIndex
ON Transactions (accountID);

CREATE INDEX timestampIndex
ON Transactions (timestamp);
