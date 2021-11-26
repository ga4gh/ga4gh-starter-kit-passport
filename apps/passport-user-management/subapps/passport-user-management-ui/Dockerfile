FROM node:16.13.0 as builder

WORKDIR /usr/src/build
COPY package-lock.json package.json react.html tsconfig.json webpack.config.server.js webpack.config.ui.js ./
COPY source source
RUN npm install
RUN npm run build

FROM node:16.13.0-alpine3.14

WORKDIR /usr/src/app
COPY --from=builder /usr/src/build/dist dist

WORKDIR /usr/src/app/dist

ENTRYPOINT [ "node", "server.js" ]
