host := `uname -a`

create-cluster:
  talosctl cluster create
  talosctl patch mc -n 10.5.0.2,10.5.0.3 --patch @patch.yaml

cilium:
  helm repo add cilium https://helm.cilium.io/
  helm repo update
  helm template \
    cilium \
    cilium/cilium \
    --version 1.15.6 \
    --namespace kube-system \
    --set ipam.mode=kubernetes \
    --set kubeProxyReplacement=false \
    --set securityContext.capabilities.ciliumAgent="{CHOWN,KILL,NET_ADMIN,NET_RAW,IPC_LOCK,SYS_ADMIN,SYS_RESOURCE,DAC_OVERRIDE,FOWNER,SETGID,SETUID}" \
    --set securityContext.capabilities.cleanCiliumState="{NET_ADMIN,SYS_ADMIN,SYS_RESOURCE}" \
    --set cgroup.autoMount.enabled=false \
    --set cgroup.hostRoot=/sys/fs/cgroup | kubectl apply -f -

metrics-server:
  kubectl apply -f https://raw.githubusercontent.com/alex1989hu/kubelet-serving-cert-approver/main/deploy/standalone-install.yaml
  kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

reloader:
  kubectl apply -k reloader

