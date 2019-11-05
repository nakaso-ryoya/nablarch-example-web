package com.nablarch.example.app.web.action;

import java.util.Objects;

import com.nablarch.example.app.web.common.authentication.context.LoginUserPrincipal;

import nablarch.core.log.Logger;
import nablarch.core.log.LoggerManager;
import nablarch.core.repository.SystemRepository;
import nablarch.fw.ExecutionContext;
import nablarch.fw.Handler;
import nablarch.fw.dicontainer.Container;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

/**
 * ログイン状態チェックハンドラ 。
 *
 * @author Nabu Rakutaro
 */
public class LoginUserPrincipalCheckHandler implements Handler<HttpRequest, Object> {

    /** ロガー **/
    private static final Logger LOGGER = LoggerManager.get(LoginUserPrincipalCheckHandler.class);

    public LoginUserPrincipalCheckHandler() {
        LOGGER.logDebug(getClass().getName() + " created.");
    }

    /**
     * セッションからユーザ情報を取得できなかった場合は、ログイン画面を表示。
     *
     * @param request リクエストデータ
     * @param context 実行コンテキスト
     * @return HTTPレスポンス
     */
    @Override
    public Object handle(HttpRequest request, ExecutionContext context) {
        Container container = SystemRepository.get(Container.class.getName());
        LoginUserPrincipal userContext = container.getComponent(LoginUserPrincipal.class);
        if (userContext.getUserId() == null
                && !Objects.equals(request.getRequestPath(), "/action/login")) {
            return new HttpResponse("/WEB-INF/view/login/index.jsp");
        }
        return context.handleNext(request);
    }
}
