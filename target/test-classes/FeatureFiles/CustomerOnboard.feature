Feature: Customer Onboard in cledger and check profile creation entry updted to CE

Scenario: Upload xml file in c_ledger
Given launch cledger and login as admin
And Prepare with the xml file
When Upload xml file
And Approve xml
Then open another tab to launch ce
When login as admin
Then verify the entry for customer and legalrep onboard
