#!/usr/bin/env make
.PHONY: run_studentmanager stop_studentmanager install_kind create_kind_cluster create_docker_registry \
connect_registry_to_kind_network connect_registry_to_kind delete_kind_cluster delete_docker_registry \
create_kind_cluster_with_registry delete_kind_cluster_with_registry install_app check_if_registry_exists \
install_kubectl push_app_to_registry install_ingress_nginx

run_studentmanager:
	docker build -t studentmanager:1.0.1  . && \
		docker run -itd -p 8080:8080 --name studentmanager --rm studentmanager:1.0.1

stop_studentmanager:
	docker stop studentmanager

install_kind:
	curl.exe -Lo "kind-windows-amd64.exe" "https://kind.sigs.k8s.io/dl/v0.20.0/kind-windows-amd64" && \
		.\kind-windows-amd64.exe --version

create_kind_cluster: install_kind install_kubectl create_docker_registry
	.\kind-windows-amd64.exe create cluster --name managercluster --config ./kind_config.yaml  || true && \
		kubectl get nodes

install_kubectl:
	curl.exe -LO "https://dl.k8s.io/release/v1.28.2/bin/windows/amd64/kubectl.exe"

create_docker_registry:
	docker run -d -p 5000:5000 --name local-registry --restart=always registry:2

check_docker_registry_exists:
	docker ps | findstr local-registry

connect_registry_to_kind_network:
	docker network connect kind local-registry || true

connect_registry_to_kind: connect_registry_to_kind_network
	kubectl apply -f ./kind_configmap.yaml

create_kind_cluster_with_registry:
	$(MAKE) create_kind_cluster && $(MAKE) connect_registry_to_kind

delete_kind_cluster:
	.\kind-windows-amd64.exe delete cluster --name managercluster

delete_docker_registry:
	docker stop local-registry && \
		docker rm local-registry

delete_kind_cluster_with_registry:
	$(MAKE) delete_kind_cluster && $(MAKE) delete_docker_registry

push_app_to_registry:
	docker push 127.0.0.1:5000/studentmanager:1.0.1

install_ingress_nginx:
	kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

install_app:
	helm upgrade --atomic --install studentmanager ./studentMgrCharts