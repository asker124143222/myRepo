# myRepo 2018-07-30
#myRepo brache edit from test
异常捕获的原因

    这里Exception异常，他又分为运行时异常RuntimeException和非运行时异常
    可查的异常（checked exceptions）:Exception下除了RuntimeException外的异常
    不可查的异常（unchecked exceptions）:RuntimeException及其子类和错误（Error）
    异常checked例外也回滚：在整个方法前加上 @Transactional(rollbackFor=Exception.class)
    异常unchecked例外不回滚： @Transactional(notRollbackFor=RunTimeException.class)
    如果异常被try｛｝catch｛｝了，事务就不回滚了，如果想让事务回滚必须再往外抛try｛｝catch｛throw Exception｝
