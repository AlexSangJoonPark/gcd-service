/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.kitchensink_ear.data;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.as.quickstarts.kitchensink_ear.model.Gcd;

@ApplicationScoped
public class GcdRepository {

    @Inject
    private Logger log;
    
    @Inject
    private EntityManager em;

    public Gcd findById(Long id) {
        return em.find(Gcd.class, id);
    }

    public List<Gcd> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Gcd> criteria = cb.createQuery(Gcd.class);
        Root<Gcd> gcd = criteria.from(Gcd.class);
        criteria.select(gcd);
        return em.createQuery(criteria).getResultList();
    }
    
    public Long getGcdSum() {
		Query q = em.createQuery ("SELECT SUM(gcd) FROM Gcd");
		Long result = (Long) q.getSingleResult();
        return result;
    }
    
    public void save(Gcd gcd) throws Exception {
        em.persist(gcd);
    }
}
