package hhwycache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;

/**
 * Base class for CacheManager implementations that want to support built-in
 * awareness of Spring-managed transactions. This usually needs to be switched
 * on explicitly through the {@link #setTransactionAware} bean property.
 *
 * @author Juergen Hoeller
 * @since 3.2
 * @see #setTransactionAware
 * @see TransactionAwareCacheDecorator
 * @see TransactionAwareCacheManagerProxy
 */
public abstract class HhwyCacheManager extends AbstractTransactionSupportingCacheManager {

}