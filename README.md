# excel-api
REST API based on excel evaluation.

This APi has two endpoints:
* uploading endpoint - multipart request to server for uploading Excel file.
* evaluating endpoint - by sending cell values, these are populated to Excel and immediatelly evaluated against uploaded Excel (with formulas). Response contains result.

Limitations:
* this version support one Excel 
* this version supports one SHEET inside Excel only
* there are two types of Excel file...only one is supported. Details later.


