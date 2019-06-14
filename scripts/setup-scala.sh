#!/bin/bash

# Instalando o sdkman
curl -s "https://get.sdkman.io" | bash

# Instalando Scala versão 2.11.12
sdk install scala 2.11.12

echo "Utilize essa versão na sua IDE"
echo "/home/${USER}/.sdkman/candidates/scala/2.11.12"
