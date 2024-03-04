let pointId = 0;
let points = new Map();

const elt = document.getElementById('graph');
const calculator = Desmos.GraphingCalculator(elt, {
    keypad: false,
    expressions: false,
    settingsMenu: false,

    xAxisLabel: 'x',
    yAxisLabel: 'y',
    xAxisStep: 1,
    yAxisStep: 1,
    xAxisArrowMode: Desmos.AxisArrowModes.POSITIVE,
    yAxisArrowMode: Desmos.AxisArrowModes.POSITIVE
});

calculator.setMathBounds({
    left: -5,
    right: 5,
    bottom: -5,
    top: 5
});

let newDefaultState = calculator.getState();
calculator.setDefaultState(newDefaultState);

let prevR;
function drawGraphByR(r) {
    prevR=r;
    for (let i = 0; i < pointId; i++) {
            calculator.removeExpression({id: 'point' + i});
        }
    points.forEach((v,k) => {
        drawPointXYRResID(v.x, v.y, v.r, v.result, k);
    });
    calculator.setExpression({
        id: '1',
        latex: "x >= 0\\{y>=0\\} \\{y<="+ r+"/2 -x\\}",
        color: Desmos.Colors.ORANGE
    });
    calculator.setExpression({
        id: '2',
        latex: "x <= 0\\{y>=0\\} \\{x^2 + y^2 <= "+r+"^2\\}",
        color: Desmos.Colors.ORANGE
    });
    calculator.setExpression({
        id: '3',
        latex: "x>=0 \\{x<="+r+"\\}\\{y<=0\\}\\{y>=-"+r+"\\}",
        color: Desmos.Colors.ORANGE
    });
    calculator.setExpression({id: '8', latex: 'r=' + r, lineOpacity: 0});
}


function doClear(evt){
console.log(123);
    for (let i = 0; i < pointId; i++) {
        calculator.removeExpression({id: 'point' + i});
    }
    pointId=0;
    points= new Map();
    drawGraphByR(prevR);
    console.log(prevR);
}

$("#r_text").on("input", function(event) {
    prevR = $("#r_text").val();
    console.log(44444);
    console.log(prevR);
    // removeErrorMessage();
    //drawGraphByR(prevR);
});
function drawPointXYRRes(x, y, r, result) {
    if (r === undefined)
        r = 0;
    points.set('point' + pointId, {x, y, r, result})
    prevR=r;

    calculator.setExpression({
        id: 'point' + pointId++,
        latex: '(' + x + ', ' + y + ')',
        color: result ? Desmos.Colors.GREEN : Desmos.Colors.BLUE
    });
}

function drawPointXYRResID(x, y, r, result, point_id) {
    const actualR = Number(getCurrentR());
    if (+actualR === +r) {
    prevR=r;
        calculator.setExpression({
            id: point_id,
            latex: '(' + x + ', ' + y + ')',
            color: result ? Desmos.Colors.GREEN : Desmos.Colors.BLUE
        });
    }
}

function inRectangle(point, rect) {
    return (
        point.x >= rect.left &&
        point.x <= rect.right &&
        point.y <= rect.top &&
        point.y >= rect.bottom
    )
}


elt.addEventListener('click', handleGraphClick);

function createErrorMessage(message) {
    let error_block = document.getElementById("error_block");
    let error_message_block = document.getElementById("error");
        if (error_message_block != null) {
            return;
        }
    error_block.innerHTML = "<p id='error'>"+message+"</p>";
}

function resize(R){
    for (let i = 0; i < pointId; i++) {
        let res = points.get('point' + i);
        res.x = (res.x * R) / res.r;
        res.y = (res.y * R) / res.r;
        res.r = R;
    }
    drawGraphByR(R);
}

function removeErrorMessage() {
    let error_block = document.getElementById("error");
    if (error_block == null) {
        return;
    }
    error_block.remove();
}
function handleGraphClick (evt) {
    /*if ($("#r_text").val()==null){
            createErrorMessage("Select R before pointing at the graph!");
            return;
    }*/
    //removeErrorMessage();
    const rect = elt.getBoundingClientRect();
    const x = evt.clientX - rect.left;
    const y = evt.clientY - rect.top;
    const mathCoordinates = calculator.pixelsToMath({x: x, y: y});

    if (!inRectangle(mathCoordinates, calculator.graphpaperBounds.mathCoordinates)) return;

    document.getElementById("graphSelect:graph-x").value = mathCoordinates.x;
    document.getElementById("graphSelect:graph-y").value = mathCoordinates.y;

    updateBeanValues();
}