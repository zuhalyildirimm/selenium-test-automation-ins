# selenium-test-automation-ins
 
Company QA Automation Project
This repository contains the automated test framework for validating the functionality of Insider's Careers Page, including homepage validation, job filtering, and job application processes.
Features
Home Page Validation
Ensures that the Insider homepage loads successfully.
Navigation & Careers Page Tests
Verify that the "Company" menu in the navigation bar opens correctly.
Validate that the "Careers" page is accessible and all its sections are displayed properly.
Job Filtering & Validation
Navigate to the QA jobs section.
Apply filters for:
Location: Istanbul, Turkey
Department: Quality Assurance
Ensure that only relevant job listings are displayed.
Job Details & Application Form
Validate that each job listing contains "Quality Assurance" in the Position and Department fields.
Ensure that all listed jobs are for Istanbul, Turkey.
Click on View Role and verify redirection to the Lever Application form.
Screenshots & Reporting
Screenshots captured for failed tests using Chrome DevTools Protocol (CDP).
TestNG Reports generated for structured execution logs.
CDP (Chrome DevTools Protocol) Integration
Captures HAR (HTTP Archive) network traffic logs for each test case.
Filters response payloads, previews, and request details.
Saves HAR logs as compressed ZIP files under omni/test-output/har-archives.
HAR data is structured in XML format with only relevant details.
 
 
 
 
Project Setup
Prerequisites
To set up and run the project, ensure you have the following installed:
Java 17
Maven
A supported IDE (Eclipse)
Dependencies
Ensure the following dependencies are listed in your pom.xml file:
Selenium 4 (for browser automation)
TestNG (for test execution)
CDP DevTools API (for capturing network logs & screenshots)
WebDriverManager (for managing browser drivers)
Log4j (for structured logging)
 
 
 
 
Test Execution
                                                                                                                                                                                Run All XML Reports   
                                                                                                                                        mvn clean test -DsuiteXmlFile=CompanyAllPages.xml
Run Individual Tests
mvn test -Dtest=HomePageTest
mvn test -Dtest=CareersPageTest
mvn test -Dtest=JobFilteringTest
mvn test -Dtest=JobDetailsTest
Generate Test Reports
mvn surefire-report:report
 
 
 
 
Test Scenarios Covered
1. Home Page Tests
Verify that Insider Homepage opens correctly.
2. Careers Page Tests
Navigate via Company > Careers.
Validate that the following sections are displayed:
Careers
Locations
Teams
Life at Insider
3. Job Filtering Tests
Navigate to QA Careers Page.
Click "See all QA jobs".
Apply filters for:
Location: Istanbul, Turkey
Department: Quality Assurance
Verify that only relevant job postings are displayed.
4. Job Details & Application Tests
Validate that all job listings contain "Quality Assurance" in Position and Department.
Ensure Location is Istanbul, Turkey.
Click View Role and confirm redirection to the Lever Application form.
 
 
 
 
Logging, Reporting & Network Traffic Capture
Log4j captures detailed execution logs.
TestNG Reports provide structured test results.
CDP Screenshots are stored in:
screenshots/fail/ → Failed tests
screenshots/pass/ → Successful tests
HAR Network Logs are recorded for each test and stored in har-archives/.
Only relevant HAR details (response payloads, preview, request info) are extracted.
HAR logs are saved in compressed ZIP format.
 
 
 
 
Example Paths
Screenshot Paths
Successful Test Screenshot:
screenshots/pass/jobFiltering_success.png
Failed Test Screenshot:
screenshots/fail/verifyLeverApplicationRedirect.png
HAR Log Path
HAR Log ZIP File:
verifyCareerPageBlocks-har.zip
