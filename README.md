Chennai Rising
==============

"Chennai Rising is an open platform which facilitates all like-minded people who share the will to identify and fix the problems our school children face and replicate this mission in school in every corner in the landscape of Tamil Nadu."

Our Motive:

http://www.thehindu.com/news/cities/chennai/college-students-stand-up-for-chennai-schools/article5574518.ece

Our Facebook Group:

https://www.facebook.com/groups/363458850484164/

Directory structure
-------------------

The platform combines the power of mobile apps and web UI with the backend REST APIs

* api -- contains the RESTful APIs
* cdp -- web UI to view the geolocation details of various schools with problems
* pgsql -- postgresql DB dumps
* android -- Application written to interface to the platform

Requirements
------------

* Require phalcon installed and loaded- `http://phalconphp.com/en/`
* Require Apache, PostgreSQL, PHP setup
* Require an Android device

Installation
------------

    * create database with cdp and username: cdb, password: cdb
	* Dump the pgsql/cdb.dbdump into the new cdp database
	* Pick the android/chennaiirising.apk to install the APP

Usage of REST APIs
------------------


 GET
  - schools list -> http://<localhost>/api/schools/list
  - classes list -> http://<localhost>/api/classes/<school-id>
  - teachers list -> http://<localhost>/api/staffs/<school-id>
  - students list -> http://<localhost>/api/students/<school-id>/<class-name>
  
 POST
 - Student attendance -> http://<localhost>/api/student/attendance/add
{
    "student_id": "1",
    "turnout_dt": "2014-10-14",
    "ispresent": "Y",
    "absent_reason": ""
}

-staff attendance -> http://<localhost>/api/staff/attendance/add

{
    "staff_id": "1",
    "turnout_dt": "2014-10-10",
    "ispresent": "Y",
    "absent_reason": ""
}

Credits
--------

Karnataka Learning Partnership: https://github.com/klpdotorg 
