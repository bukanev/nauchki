<head>
    <meta charset="UTF-8">
    <title>Stage Edit Page</title>
</head>
<body>
<script th:src="@{/webjars/bootstrap/4.6.0-1/js/bootstrap.bundle.min.js}"></script>
<a type="btn" href="/admin">Назад</a>
<tr>
    <td>
        <form action="#" action="/admin/stedit/${stage.id}" method="post" >
                <input type="number" HIDDEN class="form-control" id="id" name="name" value="${stage.id}"/>
            <div class="mb-3">
                <label class="form-label">Days</label>
                <input type="number" class="form-control" id="days" name="days" value="${stage.days}"/>
            </div>

            <div class="mb-3">
                <label class="form-label">Gender</label>
                <input type="text" class="form-control" id="gender" name="gender" value="${stage.gender}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight -3</label>
                <input class="form-control" name="medianHeightMinus3" value="${stage.medianHeightMinus3}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight -2</label>
                <input class="form-control" name="medianHeightMinus2" value="${stage.medianHeightMinus2}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight -1</label>
                <input class="form-control" name="medianHeightMinus1" value="${stage.medianHeightMinus1}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight</label>
                <input class="form-control" name="medianHeight" value="${stage.medianHeight}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight +1</label>
                <input class="form-control" name="medianHeightPlus1" value="${stage.medianHeightPlus1}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight +2</label>
                <input class="form-control" name="medianHeightPlus2" value="${stage.medianHeightPlus2}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight +3</label>
                <input class="form-control" name="medianHeightPlus3" value="${stage.medianHeightPlus3}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight -3</label>
                <input class="form-control" name="medianWeightMinus3" value="${stage.medianWeightMinus3}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight -2</label>
                <input class="form-control" name="medianWeightMinus2" value="${stage.medianWeightMinus2}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight -1</label>
                <input class="form-control" name="medianWeightMinus1" value="${stage.medianWeightMinus1}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight</label>
                <input class="form-control" name="medianWeight" value="${stage.medianWeight}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight +1</label>
                <input class="form-control" name="medianWeightPlus1" value="${stage.medianWeightPlus1}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight +2</label>
                <input class="form-control" name="medianWeightPlus2" value="${stage.medianWeightPlus2}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight +3</label>
                <input class="form-control" name="medianWeightPlus3" value="${stage.medianWeightPlus3}"/>
            </div>
            <div class="mb-3">
                <label class="form-label">Skills</label>
                <textarea class="text-area" style="width:300px; height:150px;" id="skills" name="skills"  value="">${stage.skills}</textarea>
            </div>
            <div class="mb-3">
                <button type="submit" class="btn-primary">Enter</button>
            </div>
        </form>
    </td>
</tr>
</body>
</html>