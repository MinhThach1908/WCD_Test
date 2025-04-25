<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Player</title>
    <script>
        function validateForm() {
            var name = document.getElementById("name").value;
            var fullName = document.getElementById("full_name").value;
            var age = document.getElementById("age").value;
            var indexId = document.getElementById("index_id").value;

            if (name == "" || fullName == "") {
                alert("Name and Full Name are required.");
                return false;
            }

            if (isNaN(age) || age == "") {
                alert("Age must be a valid number.");
                return false;
            }

            if (isNaN(indexId) || indexId == "") {
                alert("Index ID must be a valid number.");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
<h2>Create New Player</h2>

<form action="create" method="POST" onsubmit="return validateForm()">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required/><br>

    <label for="full_name">Full Name:</label>
    <input type="text" id="full_name" name="full_name" required/><br>

    <label for="age">Age:</label>
    <input type="text" id="age" name="age" required/><br>

    <label for="index_id">Index ID:</label>
    <input type="number" id="index_id" name="index_id" required/><br>

    <button type="submit">Create Player</button>
</form>

<c:if test="${not empty error}">
    <div style="color:red">${error}</div>
</c:if>

</body>
</html>
