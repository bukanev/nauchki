<head>
    <meta charset="UTF-8">
    <title>Stage Edit Page</title>
</head>
<body>
<script th:src="@{/webjars/bootstrap/4.6.0-1/js/bootstrap.bundle.min.js}"></script>
<a type="btn" href="/admin">Назад</a>
<tr>
    <td>
        <form action="#" action="/admin/st" method="post" >
            <div class="mb-3">
                <label class="form-label">Days</label>
                <input type="number" class="form-control" id="days" name="days" />
            </div>
            <div class="mb-3">
                <label class="form-label">Gender</label>
                <input type="text" class="form-control" id="gender" name="gender" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight -3</label>
                <input class="form-control" name="medianHeightMinus3"/>
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight -2</label>
                <input class="form-control" name="medianHeightMinus2" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight -1</label>
                <input class="form-control" name="medianHeightMinus1" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight</label>
                <input class="form-control" name="medianHeight" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight +1</label>
                <input class="form-control" name="medianHeightPlus1" >
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight +2</label>
                <input class="form-control" name="medianHeightPlus2" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianHeight +3</label>
                <input class="form-control" name="medianHeightPlus3" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight -3</label>
                <input class="form-control" name="medianWeightMinus3" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight -2</label>
                <input class="form-control" name="medianWeightMinus2" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight -1</label>
                <input class="form-control" name="medianWeightMinus1" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight</label>
                <input class="form-control" name="medianWeight" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight +1</label>
                <input class="form-control" name="medianWeightPlus1" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight +2</label>
                <input class="form-control" name="medianWeightPlus2" />
            </div>
            <div class="mb-3">
                <label class="form-label">MedianWeight +3</label>
                <input class="form-control" name="medianWeightPlus3" />
            </div>
            <div class="mb-3">
                <label class="text-area">Skills</label>
                <textarea type="text" class="text-area" style="width:300px; height:150px;" cols="60" rows="15" name="skills"></textarea>
            </div>
            <div class="mb-3">
                <button type="submit" class="btn-primary">Enter</button>
            </div>
        </form>
    </td>
</tr>
</body>
</html>