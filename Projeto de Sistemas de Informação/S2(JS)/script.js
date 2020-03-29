function load() {
    //Todos os elementos com a tag li
    var li = document.getElementsByTagName("li");
    //Event Listener para botao de calucar o custo
    document.getElementById("calculate").addEventListener("click", calculoCusto);

    //Event Listener para todos os li
    for (var i = 0; i < li.length; i++) {
        li[i].addEventListener("click", menu);
    }

    //Mostrar a opcao escolhida
    function menu(e) {
        document.getElementById("selectedOption").innerHTML = e.target.innerHTML;
    }

    //Calculo do custo
    function calculoCusto() {
        var custoBase = 0;
        var custoTotal = 0;
        detalhes = false;
        endEnvio = false;

        if (document.getElementById("w1").checked) {
            custoBase = 1;
            detalhes = true;
        } else if (document.getElementById("w2").checked) {
            custoBase = 10;
            detalhes = true;
        }

        custoTotal = custoBase;

        if (document.getElementById("s1").checked && document.getElementById("s2").checked) {
            custoTotal = custoTotal + 5 + (custoBase * 2.5);
        } else if (document.getElementById("s1").checked) {
            custoTotal += 5;
        } else if (document.getElementById("s2").checked) {
            custoTotal += custoBase * 2.5;
        }

        if (document.getElementById("shippingName").value.length !== 0 && document.getElementById("shippingAddress").value.length !== 0) {
            endEnvio = true;
        }

        if (!detalhes) {
            alert("Selecione o peso da encomenda!");
        } else if (!endEnvio) {
            alert("Preencha o endereço de envio!");
        } else {
            document.getElementById("shippingCost").innerHTML = custoTotal;
        }
    }
}