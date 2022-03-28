<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Page</title>
    <style>
        .table {
            width: 100%;
            margin-bottom: 20px;
            border: 1px solid #dddddd;
            border-collapse: collapse;
        }

        .table th {
            font-weight: bold;
            padding: 5px;
            background: #efefef;
            border: 1px solid #dddddd;
        }

        .table td {
            border: 1px solid #dddddd;
            padding: 5px;
        }
    </style>
</head>
<body>
<div style="margin-left: 20px;">
    <table>
        <div>
            <h1>Standart stages</h1>
            <a type="btn" href="/admin/st">Добавить</a>
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Days</th>
                    <th>Gender</th>
                    <th>Height -3</th>
                    <th>Height -2</th>
                    <th>Height -1</th>
                    <th>Height</th>
                    <th>Height +1</th>
                    <th>Height +2</th>
                    <th>Height +3</th>
                    <th>Weight -3</th>
                    <th>Weight -2</th>
                    <th>Weight -1</th>
                    <th>Weight</th>
                    <th>Weight +1</th>
                    <th>Weight +2</th>
                    <th>Weight +3</th>
                    <th>Skills</th>
                </tr>
                </thead>
                <tbody>
                <#list stages as stage>
                    <tr <#--th:each="stage : ${stages}"-->>
                        <td>${stage.id}</td>
                        <td>${stage.days}</td>
                        <td>${stage.gender}</td>
                        <td>${stage.medianHeightMinus3}</td>
                        <td>${stage.medianHeightMinus2}</td>
                        <td>${stage.medianHeightMinus1}</td>
                        <td>${stage.medianHeight}</td>
                        <td>${stage.medianHeightPlus1}</td>
                        <td>${stage.medianHeightPlus2}</td>
                        <td>${stage.medianHeightPlus3}</td>
                        <td>${stage.medianWeightMinus3}</td>
                        <td>${stage.medianWeightMinus2}</td>
                        <td>${stage.medianWeightMinus1}</td>
                        <td>${stage.medianWeight}</td>
                        <td>${stage.medianWeightPlus1}</td>
                        <td>${stage.medianWeightPlus2}</td>
                        <td>${stage.medianWeightPlus3}</td>
                        <td>${stage.skills}</td>
                        <td><a type="btn" href="/admin/stage/${stage.id}">Подробнее</a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </table>
</div>
</body>
</html>