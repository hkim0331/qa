FROM clojure:lein

ENV DEBIAN_FRONTEND=noninteractive
ENV DEBCONF_NOWARNINGS=yes

RUN set -ex ; \
    apt-get -y update; \
    apt-get -y upgrade; \
    apt-get -y --no-install-recommends install \
            sudo git git-flow openssh-client postgresql-client

# don't forget in production
RUN set -ex; apt-get -y autoremove; apt-get -y clean; rm -rf /var/lib/apt/lists/*

ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

RUN set -eux; \
    groupadd --gid $USER_GID $USERNAME; \
    useradd --uid $USER_UID --gid $USER_GID -m $USERNAME; \
    echo ${USERNAME} ALL=\(ALL\) NOPASSWD:ALL > /etc/sudoers.d/$USERNAME; \
    chmod 0440 /etc/sudoers.d/$USERNAME

USER $USERNAME
