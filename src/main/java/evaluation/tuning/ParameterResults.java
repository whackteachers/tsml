/*
 * This file is part of the UEA Time Series Machine Learning (TSML) toolbox.
 *
 * The UEA TSML toolbox is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * The UEA TSML toolbox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with the UEA TSML toolbox. If not, see <https://www.gnu.org/licenses/>.
 */
package evaluation.tuning;

import evaluation.storage.ClassifierResults;

/**
 * Simple container class for a parameter set and accompanying classifierResults, 
 * plus optionally a score which is used to order ParameterResults objects. 
 * 
 * Score defaults to the accuracy contained in the results object if not supplied
 * 
 * @author James Large (james.large@uea.ac.uk)
 */
public class ParameterResults implements Comparable<ParameterResults> { 
    public ParameterSet paras;
    public ClassifierResults results; 
    public double score;

    /**
     * Defaults to scoring by accuracy.
     */
    public ParameterResults(ParameterSet parameterSet, ClassifierResults results) {
        this.paras = parameterSet;
        this.results = results;
        this.score = results.getAcc();
    }
    
    public ParameterResults(ParameterSet parameterSet, ClassifierResults results, double score) {
        this.paras = parameterSet;
        this.results = results;
        this.score = score;
    }

    @Override
    public int compareTo(ParameterResults other) {
        return Double.compare(this.score, other.score);
    }
}
