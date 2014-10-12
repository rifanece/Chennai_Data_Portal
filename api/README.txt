


 GET
  - schools list -> http://localhost/api/schools/list
  - classes list -> http://localhost/api/classes/<school-id>
  - teachers list -> http://localhost/api/staffs/<school-id>
  - students list -> http://localhost/api/students/<school-id>/<class-name>
  
 POST
 - Student attendance -> http://localhost/api/student/attendance/add
{
    "student_id": "1",
    "turnout_dt": "2014-10-14",
    "ispresent": "Y",
    "absent_reason": ""
}

-staff attendance -> http://localhost/api/staff/attendance/add

{
    "staff_id": "1",
    "turnout_dt": "2014-10-10",
    "ispresent": "Y",
    "absent_reason": ""
}
