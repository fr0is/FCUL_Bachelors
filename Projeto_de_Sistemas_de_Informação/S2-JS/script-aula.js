function load() {
    //Todos os elementos com a tag li
    var li = document.getElementsByTagName("li");
    //Event Listener para botao de calcular o custo
    document.getElementById("calculate").addEventListener("click", calculoCusto);

    //Event Listener para adicionar novo campo
    document.getElementById("w2").addEventListener("click", adicionarNovo);

    //Event Listener para retirar novo campo
    document.getElementById("w1").addEventListener("click", removerNovo);

    function removerNovo(){
        if(document.getElementById("novoCampo") != null){
            document.getElementById("novoCampo").remove();
        }
    }
    
    function adicionarNovo(){
    if(document.getElementById("novoCampo") != null){
        return;
    }
    var botao = document.getElementById("orderLight");
    var fieldset = document.createElement("fieldset");
    fieldset.setAttribute("id","novoCampo");
    var legend = document.createElement("legend");
    legend.innerHTML = "Detalhes do transporte";
    var input1 = document.createElement("input");
    input1.setAttribute("type","radio");
    input1.setAttribute("id","t1");
    input1.setAttribute("name","transporte");
    var label1 = document.createElement("label");
    label1.setAttribute("for","t1");
    label1.innerHTML = "Por terra";
    var input2 = document.createElement("input");
    input2.setAttribute("type","radio");
    input2.setAttribute("id","t2");
    input2.setAttribute("name","transporte");
    var label2 = document.createElement("label"); 
    label2.setAttribute("for","t2");
    label2.innerHTML = "Por ar";
    document.getElementById("form1").appendChild(fieldset);     
    fieldset.appendChild(legend);   
    fieldset.appendChild(input1); 
    fieldset.appendChild(label1); 
    fieldset.appendChild(input2);
    fieldset.appendChild(label2);    
    document.getElementById("form1").insertBefore(fieldset, botao);                   
    }

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